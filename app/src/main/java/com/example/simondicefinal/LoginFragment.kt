package com.example.simondicefinal


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simondicefinal.databinding.ActivityMainBinding
import com.example.simondicefinal.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class LoginFragment : Fragment(R.layout.fragment_login) {

  lateinit var recyclerView: RecyclerView
  lateinit var users : MutableList<SimonDiceEntity>
  lateinit var adapter : SimonDiceAdapter
  private var binding : FragmentLoginBinding? = null
    private var Mainbinding : ActivityMainBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val _binding = FragmentLoginBinding.bind(view)
        binding = _binding
        Mainbinding = ActivityMainBinding.inflate(layoutInflater)
        Mainbinding!!.containerPrincipal.addView(binding!!.root)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginBinding.inflate(layoutInflater)
        users = ArrayList()
        getUsers()
        binding!!.btnGuardarUser.setOnClickListener {
            addUser(SimonDiceEntity(nombre = binding!!.txtUser.text.toString()))
        }


    }

    fun getUsers() = runBlocking {
        launch {
            users = SimonDiceApp.database.simonDao().getAll()
            setUpRecyclerView(users)
        }
    }

    fun setUpRecyclerView(users: MutableList<SimonDiceEntity>) {
        adapter = SimonDiceAdapter(users)
        recyclerView = binding!!.contenedorUsuarios
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = adapter
    }


    fun updateUser(user : SimonDiceEntity) = runBlocking {
        launch {
            user.puntuacion += 1
            SimonDiceApp.database.simonDao().updateUser(user)
        }
    }

    fun deleteUser(user : SimonDiceEntity) = runBlocking {
        launch{
            val pos = users.indexOf(user)
            SimonDiceApp.database.simonDao().deleteUser(user)
            users.remove(user)
            adapter.notifyItemRemoved(pos)
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var viewroot = inflater.inflate(R.layout.fragment_login, container, false)

        // Inflate the layout for this fragment
        return viewroot   }






    fun clearFocus(){
        binding!!.txtUser.setText("")
    }


    fun addUser(user : SimonDiceEntity) = runBlocking {
        launch {
            val id = SimonDiceApp.database.simonDao().addUser(user)
            val recoveryUser = SimonDiceApp.database.simonDao().getUserByScore()
            users.add(recoveryUser)
            adapter.notifyItemInserted(users.size)

            clearFocus()
        }
    }

}
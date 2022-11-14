package com.example.simondicefinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SimonDiceAdapter(
    val users: List<SimonDiceEntity>): RecyclerView.Adapter<SimonDiceAdapter.ViewHolder> (){

        override fun onBindViewHolder(holder:ViewHolder, position : Int) {
            var item = users[position]
            holder.bind(item)
        }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ViewHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_simondice, parent, false))
    }
    override fun getItemCount() : Int{
        return users.size
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val tvpuntuacion = view.findViewById<TextView>(R.id.tvPuntuacion)
        val tvNombre = view.findViewById<TextView>(R.id.tvNombre)
        val tvFecha = view.findViewById<TextView>(R.id.tvFecha)


        fun bind(user : SimonDiceEntity){
            itemView.setOnClickListener{

            }
            tvpuntuacion.text = user.puntuacion.toString()
            tvNombre.text = user.nombre
            tvFecha.text = user.hora.toString()
        }
    }
    }

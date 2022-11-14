package com.example.simondicefinal

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
// Si en el Graddle.app introducimos un nuevo buildFeatures,
// dentro de este, cambiaremos el estado de viewBinding a true
import com.example.simondicefinal.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {


    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.containerPrincipal.addView(LoginFragment().view)


    }
}
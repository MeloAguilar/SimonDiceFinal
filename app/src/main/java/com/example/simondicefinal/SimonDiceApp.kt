package com.example.simondicefinal

import android.app.Application
import androidx.room.Room

class SimonDiceApp : Application() {
    companion object{
        lateinit var database : SimonDataBase
    }


    override fun onCreate() {
        super.onCreate()
        database =
            Room.databaseBuilder(
                this,
                SimonDataBase::class.java,
                "tasks-db"
            ).build()
    }
}
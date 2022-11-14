package com.example.simondicefinal

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.Instant
import java.util.*

@Entity(tableName="simon_entity")
data class SimonDiceEntity (
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var puntuacion : Int = 0,
    var nombre : String = "",

    var hora : Date = Date.from(Instant.now())
)



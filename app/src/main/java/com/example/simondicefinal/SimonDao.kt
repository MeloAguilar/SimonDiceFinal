package com.example.simondicefinal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SimonDao {
    @Query("Select * From simon_entity")
    suspend fun getAll() : MutableList<SimonDiceEntity>

    @Query("Select * From simon_entity Order by puntuacion desc Limit 1")
    suspend fun getUserByScore() : SimonDiceEntity

    @Update
    suspend fun updateUser(user : SimonDiceEntity)

    @Delete
    suspend fun deleteUser(user : SimonDiceEntity)

    @Insert
    suspend fun addUser(user : SimonDiceEntity) : Long
}
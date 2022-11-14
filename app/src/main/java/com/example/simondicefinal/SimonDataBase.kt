package com.example.simondicefinal

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.*


@Database(entities = arrayOf(SimonDiceEntity::class), version = 1)
@TypeConverters(Converters::class)
abstract class SimonDataBase : RoomDatabase() {
    abstract fun simonDao(): SimonDao
}

object Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}

package com.example.fridge.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FridgeItem::class], version = 1)
abstract class FridgeDatabase : RoomDatabase() {
    // 2. Abstract Functions
    abstract fun fridgeDao(): FridgeDao
    companion object {
        @Volatile
        private var Instance: FridgeDatabase? = null

        fun getDatabase(context: Context): FridgeDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FridgeDatabase::class.java, "fridge_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
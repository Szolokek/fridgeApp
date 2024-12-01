package com.example.fridge.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "items")
data class FridgeItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
//    val expiration: Date,
    val imageData: String?
)
package com.example.fridge.data

import android.graphics.Bitmap
import kotlinx.coroutines.flow.Flow

interface FridgeRepository {
    fun getAllItems(): Flow<List<FridgeItem>>

    fun getItem(id: Int): Flow<FridgeItem>

    // 3. Data Modification Methods
    suspend fun insert(todo: FridgeItem)

    suspend fun update(todo: FridgeItem)

    suspend fun delete(todo: FridgeItem)

    fun setCapturedImage(bitmap: Bitmap?)
    fun gteCapturedImage(): Bitmap?
}
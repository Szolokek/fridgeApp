package com.example.fridge.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FridgeDao {
    // 2. Items Flow
    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<FridgeItem>>

    @Query("SELECT * from items WHERE id = :id")
    fun getItem(id: Int): Flow<FridgeItem>

    // 3. Insert Operation for Adding or Updating Items
    @Insert
    suspend fun insert(todo: FridgeItem)

    @Update
    suspend fun update(item: FridgeItem)

    // 4. Delete an Item by TodoItem.id
    @Delete
    suspend fun delete(todo: FridgeItem)
}
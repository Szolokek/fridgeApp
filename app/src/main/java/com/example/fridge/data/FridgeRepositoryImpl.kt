package com.example.fridge.data

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.Flow


class FridgeRepositoryImpl (private val fridgeDao: FridgeDao) : FridgeRepository{
    // 2. Data Retrieval
    override fun getAllItems(): Flow<List<FridgeItem>> = fridgeDao.getAllItems()

    override fun getItem(id: Int): Flow<FridgeItem> = fridgeDao.getItem(id)

    // 3. Data Modification Methods
    override suspend fun insert(todo: FridgeItem) {
        fridgeDao.insert(todo)
    }

    override suspend fun update(todo: FridgeItem) {
        fridgeDao.update(todo)
    }

    override suspend fun delete(todo: FridgeItem) {
        fridgeDao.delete(todo)
    }

    private val _capturedImage = mutableStateOf<Bitmap?>(null)
    val capturedImage: State<Bitmap?> = _capturedImage

    override fun setCapturedImage(bitmap: Bitmap?) {
        _capturedImage.value = bitmap
    }
    override fun gteCapturedImage(): Bitmap? {
        return _capturedImage.value
    }
}
package com.example.fridge.ui.camera

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fridge.data.FridgeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedImageViewModel @Inject constructor(private val fridgeRepository: FridgeRepository) : ViewModel() {
    private val _capturedImage = mutableStateOf<Bitmap?>(null)
    val capturedImage: State<Bitmap?> = _capturedImage

    fun setCapturedImage(bitmap: Bitmap) {
        _capturedImage.value = bitmap
    }

    fun onTakePhoto(bitmap: Bitmap) {
        setCapturedImage(bitmap)
        fridgeRepository.setCapturedImage(bitmap)

    }
}
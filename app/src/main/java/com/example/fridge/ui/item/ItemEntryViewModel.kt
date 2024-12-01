package com.example.fridge.ui.item

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.fridge.data.FridgeItem
import com.example.fridge.data.FridgeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.ByteArrayOutputStream
import javax.inject.Inject


@HiltViewModel
class ItemEntryViewModel @Inject constructor( private val fridgeRepository: FridgeRepository) : ViewModel() {

    /**
     * Holds current item ui state
     */
    var itemUiState by mutableStateOf(ItemUiState())
        private set
    fun refreshData(){
    updateUiState(itemUiState.itemDetails.copy(imageData = fridgeRepository.gteCapturedImage()))
}
    /**
     * Updates the [itemUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState =
            ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && name.isNotBlank() && name.isNotBlank()
        }
    }

    suspend fun saveItem() {
        if (validateInput()) {
            fridgeRepository.insert(itemUiState.itemDetails.toItem())
        }
        fridgeRepository.setCapturedImage(null)
    }


    fun onTakePhoto(bitmap: Bitmap) {
        updateUiState(itemUiState.itemDetails.copy(imageData = bitmap))
        Log.i("viewmodel", itemUiState.itemDetails.imageData.toString())
        itemUiState.itemDetails.toItem().imageData?.let { Log.i("viewmodel", it) }


    }





}

/**
 * Represents Ui State for an Item.
 */
data class ItemUiState(
    val itemDetails: ItemDetails = ItemDetails(),
    val isEntryValid: Boolean = false
)

data class ItemDetails(
    val id: Int = 0,
    val name: String = "",
    val imageData: Bitmap? = null
)

/**
 * Extension function to convert [ItemDetails] to [Item]. If the value of [ItemDetails.price] is
 * not a valid [Double], then the price will be set to 0.0. Similarly if the value of
 * [ItemDetails.quantity] is not a valid [Int], then the quantity will be set to 0
 */
fun ItemDetails.toItem(): FridgeItem = FridgeItem(
    id = id,
    name = name,
    imageData = imageData?.let { converterBitmapToString(it) }
)

fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    return outputStream.toByteArray()
}

fun converterBitmapToString(bitmap: Bitmap): String {
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos)
    val byteArray = baos.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

/**
 * Extension function to convert [Item] to [ItemUiState]
 */
fun FridgeItem.toItemUiState(isEntryValid: Boolean = false): ItemUiState = ItemUiState(
    itemDetails = this.toItemDetails(),
    isEntryValid = isEntryValid
)

/**
 * Extension function to convert [Item] to [ItemDetails]
 */
fun FridgeItem.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    name = name,
    imageData = imageData?.let { converterStringToBitmap(it) }
)

fun byteArrayToBitmap(byteArray: ByteArray): Bitmap {
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

fun converterStringToBitmap(encodedString: String): Bitmap? {
    return try {
        val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
    } catch (e:Exception) {
        e.printStackTrace()
        return null
    }
}

package com.example.fridge.ui.home

import app.cash.turbine.turbineScope
import com.example.fridge.data.FridgeItem
import com.example.fridge.data.FridgeRepositoryMock
import com.example.fridge.data.FridgeRepositoryMockDelegate
import com.example.fridge.testingUtils.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HomeViewModelTest :
    FridgeRepositoryMock by FridgeRepositoryMockDelegate() {

    private lateinit var viewmodel: HomeViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()



    private fun givenAViewModel() {
        viewmodel = HomeViewModel(
            itemsRepository = fridgeRepository
        )
    }

    @Test
    fun `initial data received when the query is empty`() = runTest {
        turbineScope {
            val testData = List(5){
                FridgeItem(
                    id = it,
                    name = "name ${it}",
                    imageData = null
                )
            }
            givenAllItems(testData)
            givenAViewModel()
            val stateTurbine = viewmodel.homeUiState.testIn(this)
            val firstReceivedData  = stateTurbine.awaitItem()
            assertTrue(firstReceivedData.itemList.isNotEmpty())
            assertEquals(testData,firstReceivedData.itemList)
            stateTurbine.cancel()
        }
    }

    @Test
    fun `initial data received when query `() = runTest {
        turbineScope {
            val testData = List(5){ id ->
                FridgeItem(
                    id = id,
                    name = "name $id",
                    imageData = null
                )
            }
            givenAllItems(testData)
            givenAViewModel()
            val stateTurbine = viewmodel.homeUiState.testIn(this)
            stateTurbine.awaitItem()
            viewmodel.updateSearchQuery("2")
            val secondReceivedData  = stateTurbine.awaitItem()
            assertEquals(1 ,secondReceivedData.itemList.size)
            stateTurbine.cancel()
        }
    }

}
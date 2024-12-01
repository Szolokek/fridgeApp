package com.example.fridge.data

import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.flow.flowOf

interface FridgeRepositoryMock{
    val fridgeRepository : FridgeRepository

    fun givenAllItems(items : List<FridgeItem>)
}

class FridgeRepositoryMockDelegate : FridgeRepositoryMock{
    override val fridgeRepository : FridgeRepository = mock<FridgeRepository>(mode = MockMode.autoUnit)
    override fun givenAllItems(items: List<FridgeItem>) {
        everySuspend {
            fridgeRepository.getAllItems()
        } returns flowOf(items)
    }
}
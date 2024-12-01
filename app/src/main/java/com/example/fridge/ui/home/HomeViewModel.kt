package com.example.fridge.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fridge.data.FridgeItem
import com.example.fridge.data.FridgeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(itemsRepository: FridgeRepository) : ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
//    val homeUiState: StateFlow<HomeUiState> =
//        itemsRepository.getAllItems().map { HomeUiState(it) }
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
//                initialValue = HomeUiState()
//            )

    private val searchQuery = MutableStateFlow("")

    val homeUiState: StateFlow<HomeUiState> =
        combine(
            itemsRepository.getAllItems(),
            searchQuery
        ) { items, query ->
            val filteredItems = if (query.isEmpty()) {
                items
            } else {
                items.filter { it.name.contains(query, ignoreCase = true) }
            }
            HomeUiState(filteredItems)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUiState()
        )

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }
}

/**
 * Ui State for HomeScreen
 */
data class HomeUiState(val itemList: List<FridgeItem> = listOf())
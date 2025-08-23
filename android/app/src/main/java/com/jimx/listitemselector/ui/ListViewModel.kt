package com.jimx.listitemselector.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimx.listitemselector.data.Datasource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class ListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ListUiState())
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<ListUiEvent>()

    val events: SharedFlow<ListUiEvent> = _events.asSharedFlow()

    fun reset() {
        _uiState.value = ListUiState(Datasource().loadItems(1))
    }

    fun choose() {
        val chosenRandomId = getRandomId();
        if (chosenRandomId == null)
            return

        _uiState.update {
            it.copy(currentSelectedItemId = chosenRandomId)
        }

        val chosenRandomItem = _uiState.value.items.first({ it.id == chosenRandomId })
        viewModelScope.launch {
            _events.emit(ListUiEvent.NotifyAboutSelectedListItem(chosenRandomItem))
        }
    }

    fun getRandomId(): Int? {
        val currentItems = _uiState.value.items;
        if (currentItems.isEmpty())
            return null

        return currentItems.get(Random.nextInt(currentItems.size)).id
    }

    init {
        reset()
    }
}
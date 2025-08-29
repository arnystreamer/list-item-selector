package com.jimx.listitemselector.ui.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimx.listitemselector.data.list.ListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

@HiltViewModel
class ListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repo: ListRepository
) : ViewModel() {

    private val categoryId: Int = checkNotNull(savedStateHandle["categoryId"])

    private val _uiState = MutableStateFlow(ListUiState())
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<ListUiEvent>()
    val events: SharedFlow<ListUiEvent> = _events.asSharedFlow()

    fun reset() {
        viewModelScope.launch {
            repo.loadListItems(categoryId)
                .onStart {
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            isFinishedWithError = false
                        )
                    }
                }
                .catch { e ->
                    _events.emit(ListUiEvent.NotifyAboutError(e.message))
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isFinishedWithError = true
                        )
                    }
                }
                .collect { items ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isFinishedWithError = false,
                            items = items
                        )
                    }
                }
        }

        viewModelScope.launch {
            repo.refresh(categoryId)
        }
    }

    fun choose() {
        val chosenRandomId = getRandomId()
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
        val currentItems = _uiState.value.items
        if (currentItems.isEmpty())
            return null

        return currentItems.get(Random.nextInt(currentItems.size)).id
    }

    init {
        reset()
    }
}
package com.jimx.listitemselector.ui.list

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimx.listitemselector.data.list.ListRepository
import com.jimx.listitemselector.model.CategoryData
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

    private val _uiState = MutableStateFlow<ListUiState>(ListUiState.Loading)
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<ListUiEvent>()
    val events: SharedFlow<ListUiEvent> = _events.asSharedFlow()

    fun reset() {

        Log.d("ListViewModel", "reset")

        _uiState.update {
            Log.d("ListViewModel.reset", "ListUiState.Loading")
            ListUiState.Loading
        }

        viewModelScope.launch {

            repo.loadListItems(categoryId)
                .onStart {
                    _uiState.update {
                        ListUiState.Loading
                    }
                }
                .catch { e ->
                    Log.e("ListViewModel", e.message ?: "Unknown error")
                    _events.emit(ListUiEvent.NotifyAboutError(e.message))
                    _uiState.update {
                        ListUiState.Error(e.message ?: "Unknown error")

                    }
                }
                .collect { items ->
                    _uiState.update {
                        Log.d("ListViewModel.reset", "ListUiState.Success")
                        ListUiState.Success(CategoryData(categoryId, ""), items, null)
                    }
                }
        }

        viewModelScope.launch {
            try {
                repo.refresh(categoryId)
            } catch (e: Exception) {
                Log.e("ListViewModel", e.message ?: "Unknown error")
                _events.emit(ListUiEvent.NotifyAboutError(e.message))
            }
        }
    }

    fun choose() {

        val currentUiState = _uiState.value;

        if (currentUiState !is ListUiState.Success)
        {
            Log.e("ListViewModel.choose", "Unexpected state")
            viewModelScope.launch {
                _events.emit(ListUiEvent.NotifyAboutError("Unexpected state"))
            }
            return;
        }

        val chosenRandomId = getRandomId(currentUiState)
        if (chosenRandomId == null)
            return

        val chosenRandomItem = currentUiState.items.first({ it.id == chosenRandomId })

        _uiState.update {
            ListUiState.Success(CategoryData(categoryId, ""), currentUiState.items, chosenRandomId)
        }

        viewModelScope.launch {
            _events.emit(ListUiEvent.NotifyAboutSelectedListItem(chosenRandomItem))
        }
    }

    fun getRandomId(uiState: ListUiState.Success): Int? {
        val currentItems = uiState.items
        if (currentItems.isEmpty())
            return null

        return currentItems.get(Random.nextInt(currentItems.size)).id
    }

    init {
        reset()
    }
}
package com.jimx.listitemselector.ui.listitem

import android.util.Log
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ListItemViewModel @Inject constructor(
        savedStateHandle: SavedStateHandle,
        private val repo: ListRepository
) : ViewModel() {

    private val categoryId: Int = checkNotNull(savedStateHandle["categoryId"])
    private val itemId: Int = checkNotNull(savedStateHandle["itemId"])
    private val _uiState = MutableStateFlow(ListItemUiState(true, null, null))
    val uiState: StateFlow<ListItemUiState> = _uiState.asStateFlow()
    private val _events = MutableSharedFlow<ListItemUiEvent>()
    val events: SharedFlow<ListItemUiEvent> = _events.asSharedFlow()

    fun reset() {
        Log.d("ListItemViewModel", "reset")

        _uiState.update {
            Log.d("ListItemViewModel.reset", "ListItemUiState.Loading")
            ListItemUiState(true, null, null)
        }

        viewModelScope.launch {
            repo.loadListItems(categoryId)
                .catch { e ->
                    val errorMessage = e.message ?: "Unknown error"
                    Log.e("ListItemViewModel.reset", errorMessage)
                    _events.emit(ListItemUiEvent.NotifyAboutError(errorMessage))
                    _uiState.update {
                        ListItemUiState(false, errorMessage, null)
                    }
                }
                .collect { items ->
                    val item = items.first({ it.id == itemId })
                    _uiState.update {
                        Log.d("ListItemViewModel.reset", "ListItemUiState.Success")
                        ListItemUiState(false, null, item)
                    }
                }
        }
    }

    fun finishedConfirmationDialogOpen()
    {
        Log.d("ListItemViewModel", "finishedConfirmationDialogOpen")
        _uiState.update {
            it.copy(
                finishedConfirmationDialog = true,
                deleteConfirmationDialog = false,
                editDialog = false)
        }
    }

    fun finishedConfirmationDialogClose()
    {
        Log.d("ListItemViewModel", "finishedConfirmationDialogClose")
        _uiState.update {
            it.copy(finishedConfirmationDialog = false)
        }
    }

    fun deleteConfirmationDialogOpen()
    {
        Log.d("ListItemViewModel", "deleteConfirmationDialogOpen")
        _uiState.update {
            it.copy(
                finishedConfirmationDialog = false,
                deleteConfirmationDialog = true,
                editDialog = false)
        }
    }

    fun deleteConfirmationDialogClose()
    {
        Log.d("ListItemViewModel", "deleteConfirmationDialogClose")
        _uiState.update {
            it.copy(deleteConfirmationDialog = false)
        }
    }

    fun editDialogOpen()
    {
        Log.d("ListItemViewModel", "editDialogOpen")
        _uiState.update {
            it.copy(finishedConfirmationDialog = false,
                deleteConfirmationDialog = false,
                editDialog = true)
        }
    }

    fun editDialogClose()
    {
        Log.d("ListItemViewModel", "editDialogClose")
        _uiState.update {
            it.copy(editDialog = false)
        }
    }

    fun excludeFromChoosing()
    {
        Log.d("ListItemViewModel", "excludeFromChoosing")
    }

    fun delete()
    {
        _uiState.update {
            it.copy(isRemoteOperationInProgress = true)
        }

        Log.d("ListItemViewModel", "delete")
    }

    fun edit()
    {
        Log.d("ListItemViewModel", "edit")
    }

    init {
        reset()
    }
}


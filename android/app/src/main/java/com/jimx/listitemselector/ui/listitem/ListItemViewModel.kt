package com.jimx.listitemselector.ui.listitem

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimx.listitemselector.data.list.ListRepository
import com.jimx.listitemselector.model.ItemData
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

    val emptyEditData: ListItemUiState.AddUiData
        get() = ListItemUiState.AddUiData(false, ItemData(0, "", null, false))

    private val categoryId: Int = checkNotNull(savedStateHandle["categoryId"])
    private val itemId: Int = checkNotNull(savedStateHandle["itemId"])
    private val _uiState = MutableStateFlow(ListItemUiState(null, null, emptyEditData))
    val uiState: StateFlow<ListItemUiState> = _uiState.asStateFlow()
    private val _events = MutableSharedFlow<ListItemUiEvent>()
    val events: SharedFlow<ListItemUiEvent> = _events.asSharedFlow()

    fun reset() {
        Log.d("ListItemViewModel", "reset")

        _uiState.update {
            Log.d("ListItemViewModel.reset", "ListItemUiState.Loading")
            ListItemUiState(null, null, emptyEditData)
        }

        viewModelScope.launch {
            repo.loadListItems(categoryId)
                .catch { e ->
                    val errorMessage = e.message ?: "Unknown error"
                    Log.e("ListItemViewModel.reset", errorMessage)
                    _events.emit(ListItemUiEvent.NotifyAboutError(errorMessage))
                    _uiState.update {
                        ListItemUiState(errorMessage, null, emptyEditData)
                    }
                }
                .collect { items ->
                    val item = items.firstOrNull({ it.id == itemId })
                    _uiState.update {
                        Log.d("ListItemViewModel.reset", "ListItemUiState.Success")
                        ListItemUiState(
                            null,
                            ListItemUiState.Data(item, ListItemUiState.OpenedDialog.None),
                            emptyEditData
                        )
                    }
                }
        }
    }

    fun finishedConfirmationDialogOpen()
    {
        Log.d("ListItemViewModel", "finishedConfirmationDialogOpen")

        val data = _uiState.value.data
        if (data == null || data.item == null) {
            throw Exception("Unexpected state")
        }

        if (data.item.isExcluded) {
            throw Exception("Item is already excluded")
        }

        _uiState.update {
            it.copy(
                data = it.data?.copy(openedDialog = ListItemUiState.OpenedDialog.Finished)
            )
        }
    }

    fun finishedConfirmationDialogClose()
    {
        Log.d("ListItemViewModel", "finishedConfirmationDialogClose")
        _uiState.update {
            it.copy(
                data = it.data?.copy(openedDialog = ListItemUiState.OpenedDialog.None)
            )
        }
    }

    fun deleteConfirmationDialogOpen()
    {
        Log.d("ListItemViewModel", "deleteConfirmationDialogOpen")
        _uiState.update {
            it.copy(
                data = it.data?.copy(openedDialog = ListItemUiState.OpenedDialog.Delete)
            )
        }
    }

    fun deleteConfirmationDialogClose()
    {
        Log.d("ListItemViewModel", "deleteConfirmationDialogClose")
        _uiState.update {
            it.copy(
                data = it.data?.copy(openedDialog = ListItemUiState.OpenedDialog.None)
            )
        }
    }

    fun editScreenOpen()
    {
        val data = _uiState.value.data
        if (data == null || data.item == null) {
            throw Exception("Unexpected state")
        }

        _uiState.update {
            it.copy(
                editData = it.editData.copy(true, data.item)
            )
        }
    }

    fun editScreenClose()
    {
        _uiState.update {
            it.copy(
                editData = emptyEditData
            )
        }
    }

    fun excludeFromChoosing()
    {
        Log.d("ListItemViewModel", "excludeFromChoosing")

        val data = _uiState.value.data
        if (data == null || data.item == null) {
            throw Exception("Unexpected state")
        }

        if (data.item.isExcluded) {
            throw Exception("Item is already excluded")
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(isRemoteOperationInProgress = true)
            }

            try {
                val updatedItemData = data.item.copy(isExcluded = true)
                repo.editListItem(categoryId, updatedItemData)
                _events.emit(ListItemUiEvent.NotifyAfterFinish(updatedItemData))
            }
            catch (e: Exception) {
                val errorMessage = e.message ?: "Unknown error"
                Log.e("ListItemViewModel.excludeFromChoosing", errorMessage)
                _events.emit(ListItemUiEvent.NotifyAboutError(errorMessage))
            }
            finally {
                _uiState.update {
                    it.copy(isRemoteOperationInProgress = false)
                }
            }
        }
    }

    fun saveModifiedItem(itemData: ItemData)
    {
        Log.d("ListItemViewModel", "saveModifiedItem")

        val data = _uiState.value.data
        if (data == null || data.item == null) {
            throw Exception("Unexpected state")
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(isRemoteOperationInProgress = true)
            }

            try {
                repo.editListItem(categoryId, ItemData(data.item.id, itemData.name, itemData.description, data.item.isExcluded))
            }
            catch (e: Exception) {
                val errorMessage = e.message ?: "Unknown error"
                Log.e("ListItemViewModel.saveModifiedItem", errorMessage)
                _events.emit(ListItemUiEvent.NotifyAboutError(errorMessage))
            }
            finally {
                _uiState.update {
                    it.copy(
                        isRemoteOperationInProgress = false,
                        editData = it.editData.copy(false, ItemData(0, "", null, false)))
                }
            }
        }
    }

    fun delete()
    {
        Log.d("ListItemViewModel", "delete")

        val data = _uiState.value.data
        if (data == null || data.item == null) {
            throw Exception("Unexpected state")
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(isRemoteOperationInProgress = true)
            }

            try {
                repo.deleteListItem(categoryId, data.item)
                _events.emit(ListItemUiEvent.NotifyAfterDelete(data.item))
            }
            catch (e: Exception) {
                val errorMessage = e.message ?: "Unknown error"
                Log.e("ListItemViewModel.delete", errorMessage)
                _events.emit(ListItemUiEvent.NotifyAboutError(errorMessage))
            }
        }
    }

    init {
        reset()
    }
}


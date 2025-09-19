package com.jimx.listitemselector.ui.list

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimx.listitemselector.R
import com.jimx.listitemselector.data.list.ListRepository
import com.jimx.listitemselector.model.CategoryData
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

    private val _uiState = MutableStateFlow<ListUiState>(ListUiState(
        null,
        null,
        ListUiState.AddUiData(false, ItemData(0, "", "", false))))
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<ListUiEvent>()
    val events: SharedFlow<ListUiEvent> = _events.asSharedFlow()

    fun reset() {

        Log.d("ListViewModel", "reset")


        _uiState.update {
            Log.d("ListViewModel.reset", "ListUiState.Loading")
            it.copy(
                errorMessage = null,
                data = null
            )
        }

        viewModelScope.launch {
            repo.loadListItems(categoryId)
                .onStart {
                    _uiState.update {
                        it.copy(
                            errorMessage = null,
                            data = null
                        )
                    }
                }
                .catch { e ->
                    val errorMessage = e.message ?: "Unknown error"
                    Log.e("ListViewModel", errorMessage)
                    _events.emit(ListUiEvent.NotifyAboutError(e.message))
                    _uiState.update {
                        it.copy(errorMessage = errorMessage)
                    }
                }
                .collect { items ->
                    _uiState.update {
                        Log.d("ListViewModel.reset", "ListUiState.Success")
                        it.copy(
                            errorMessage = null,
                            data = ListUiState.Data(CategoryData(categoryId, ""), items))
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

        if (!currentUiState.isOk)
        {
            Log.e("ListViewModel.choose", "Unexpected state")
            viewModelScope.launch {
                _events.emit(ListUiEvent.NotifyAboutError("Unexpected state"))
            }
            return;
        }

        val currentData = currentUiState.data!!

        val chosenRandomItem = getRandomId(currentData)
        if (chosenRandomItem != null) {
            _uiState.update {
                it.copy(
                    data = ListUiState.Data(CategoryData(categoryId, ""), currentData.items, chosenRandomItem)
                )
            }
        }

        viewModelScope.launch {
            _events.emit(ListUiEvent.NotifyAboutSelectedListItem(chosenRandomItem))
        }
    }

    fun getRandomId(data: ListUiState.Data): ItemData? {
        val currentItems = data.items.filter { !it.isExcluded }
        return if (currentItems.isEmpty()) {
            null
        } else {
            currentItems[Random.nextInt(currentItems.size)]
        }
    }

    fun openAddForm()
    {
        _uiState.value.let {
            val addData = it.addData;
            if (addData.isOpen)
                throw Exception("Unexpected state: dialog is already open")

            _uiState.update { s -> s.copy(
                addData = addData.copy(
                    isOpen = true,
                    item = ItemData(0, "", null, false)))
            }

        }
    }

    fun dismissAddForm()
    {
        _uiState.value.let {
            val addData = it.addData;
            if (!addData.isOpen)
                throw Exception("Unexpected state: dialog is closed")

            _uiState.update {
                it.copy(addData = addData.copy(
                    isOpen = false,
                    item = ItemData(0, "", null, false)))
            }
        }
    }

    fun saveNewListItem(itemData: ItemData)
    {
        _uiState.value.let {
            val addData = it.addData
            if (!addData.isOpen)
                throw Exception("Unexpected state: dialog is closed")

            viewModelScope.launch {
                _uiState.update { s -> s.copy(isRemoteOperationInProgress = true) }
                repo.addListItem(categoryId, itemData)
                _uiState.update { s -> s.copy(
                    isRemoteOperationInProgress = false,
                    addData = addData.copy(
                        isOpen = false,
                        item = ItemData(0, "", null, false)))
                }
            }
        }
    }

    init {
        reset()
    }
}
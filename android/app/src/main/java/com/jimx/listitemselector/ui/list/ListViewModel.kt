package com.jimx.listitemselector.ui.list

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimx.listitemselector.data.category.CategoryRepository
import com.jimx.listitemselector.data.list.ListRepository
import com.jimx.listitemselector.model.CategoryData
import com.jimx.listitemselector.model.ItemData
import com.jimx.listitemselector.ui.list.ListUiState.EditCategoryUiData
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
    private val repo: ListRepository,
    private val categoryRepo: CategoryRepository
) : ViewModel() {

    val emptyCategoryEditData: EditCategoryUiData
        get() = EditCategoryUiData(false, CategoryData(0, ""))

    val emptyAddData: ListUiState.AddUiData
        get() = ListUiState.AddUiData(false, ItemData(0, "", null, false))

    private val categoryId: Int = checkNotNull(savedStateHandle["categoryId"])

    private val _uiState = MutableStateFlow(ListUiState(
        null,
        null,
        null,
        emptyCategoryEditData,
        emptyAddData))
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
            try {
                val categoryData = categoryRepo.getCategory(categoryId)
                _uiState.update {
                    it.copy(
                        categoryData = categoryData
                    )
                }
            }
            catch (e: Exception) {
                val errorMessage = e.message ?: "Unknown error"
                Log.e("ListViewModel.reset", errorMessage)
                _events.emit(ListUiEvent.NotifyAboutError(e.message))
            }
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
                            data = ListUiState.Data(items))
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

        Log.d("ListViewModel", "choose")

        val currentUiState = _uiState.value

        if (!currentUiState.isOk)
        {
            Log.e("ListViewModel.choose", "Unexpected state")
            viewModelScope.launch {
                _events.emit(ListUiEvent.NotifyAboutError("Unexpected state"))
            }
            return
        }

        val currentData = currentUiState.data!!

        val randomResult = getRandomItem(currentData)
        if (randomResult != null) {
            _uiState.update {
                it.copy(
                    data = ListUiState.Data(currentData.items, ListUiState.OpenedDialog.None,
                        randomResult.item)
                )
            }
        }

        viewModelScope.launch {
            _events.emit(ListUiEvent.NotifyAboutSelectedListItem(randomResult?.index, randomResult?.item))
        }
    }

    fun getRandomItem(data: ListUiState.Data): RandomResult? {
        val currentItems = data.items.filter { !it.isExcluded }
        return if (currentItems.isEmpty()) {
            null
        } else {
            val selectedItem = currentItems[Random.nextInt(currentItems.size)]
            val selectedIndex = data.items.indexOf(selectedItem)
            RandomResult(selectedIndex, selectedItem)
        }
    }

    data class RandomResult(val index: Int, val item: ItemData)

    fun openAddForm()
    {
        Log.d("ListViewModel", "openAddForm")

        _uiState.value.let {
            val addData = it.addData
            if (addData.isOpen)
                throw Exception("Unexpected state: dialog is already open")

            _uiState.update { s -> s.copy(
                addData = emptyAddData.copy(isOpen = true))
            }

        }
    }

    fun dismissAddForm()
    {
        Log.d("ListViewModel", "dismissAddForm")

        _uiState.value.let {
            val addData = it.addData
            if (!addData.isOpen)
                throw Exception("Unexpected state: dialog is closed")

            _uiState.update {
                it.copy(addData = emptyAddData.copy(isOpen = false))
            }
        }
    }

    fun saveNewListItem(itemData: ItemData)
    {
        Log.d("ListViewModel", "saveNewListItem")

        _uiState.value.let {
            val addData = it.addData
            if (!addData.isOpen)
                throw Exception("Unexpected state: dialog is closed")

            viewModelScope.launch {
                _uiState.update { s -> s.copy(isRemoteOperationInProgress = true) }
                try {
                    repo.addListItem(categoryId, itemData)
                    _uiState.update { s ->
                        s.copy(
                            addData = emptyAddData
                        )
                    }
                }
                catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown error"
                    Log.e("ListViewModel.saveNewListItem", errorMessage)
                    _events.emit(ListUiEvent.NotifyAboutError(errorMessage))
                }
                finally {
                    _uiState.update { s ->
                        s.copy(isRemoteOperationInProgress = false)
                    }
                }
            }
        }
    }

    fun categoryEditScreenOpen() {

        Log.d("ListViewModel", "categoryEditScreenOpen")

        _uiState.value.let {
            val categoryData = it.categoryData

            if (categoryData == null)
                throw Exception("Unexpected state: category data is missing")

            _uiState.update {
                it.copy(editCategoryData = emptyCategoryEditData.copy(isOpen = true, item = categoryData))
            }
        }
    }

    fun categoryEditScreenClose() {

        Log.d("ListViewModel", "categoryEditScreenClose")

        _uiState.update {
            it.copy(editCategoryData = emptyCategoryEditData.copy(isOpen = false))
        }
    }

    fun categoryDeleteConfirmationDialogOpen() {

        Log.d("ListViewModel", "categoryDeleteConfirmationDialogOpen")

        _uiState.value.let {
            val data = it.data

            if (data == null)
                throw Exception("Unexpected state: data is missing")

            _uiState.update {
                it.copy(data = data.copy(openedDialog = ListUiState.OpenedDialog.Delete))
            }
        }
    }

    fun categoryDeleteConfirmationDialogClose() {

        Log.d("ListViewModel", "categoryDeleteConfirmationDialogClose")

        _uiState.value.let {
            val data = it.data

            if (data == null)
                throw Exception("Unexpected state: data is missing")

            _uiState.update {
                it.copy(data = data.copy(openedDialog = ListUiState.OpenedDialog.None))
            }
        }
    }

    fun saveModifiedCategory(categoryData: CategoryData) {

        Log.d("ListViewModel", "saveModifiedCategory: $categoryData")

        val data = _uiState.value.data
        val editCategoryData = _uiState.value.editCategoryData

        if (data == null) {
            throw Exception("Unexpected state: data is missing")
        }

        if (editCategoryData.item.id == emptyCategoryEditData.item.id) {
            throw Exception("Unexpected state: edited value is empty")
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(isRemoteOperationInProgress = true)
            }

            try
            {
                categoryRepo.editCategory(CategoryData(editCategoryData.item.id, categoryData.name))
                _uiState.update {
                    it.copy(categoryData = editCategoryData.item.copy(name = categoryData.name))
                }
            }
            catch (e: Exception) {
                val errorMessage = e.message ?: "Unknown error"
                Log.e("ListViewModel.saveModifiedCategory", errorMessage)
                _events.emit(ListUiEvent.NotifyAboutError(errorMessage))
            }
            finally {
                _uiState.update {
                    it.copy(
                        isRemoteOperationInProgress = false,
                        editCategoryData = emptyCategoryEditData.copy(isOpen = false)
                    )
                }
            }
        }
    }

    fun categoryDelete() {
        Log.d("ListViewModel", "categoryDelete")

        val data = _uiState.value.data
        val categoryData = _uiState.value.categoryData

        if (data == null || categoryData == null) {
            throw Exception("Unexpected state: data or categoryData is missing")
        }

        if (!data.items.isEmpty()) {
            throw Exception("Unexpected state: cannot delete category with items")
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(isRemoteOperationInProgress = true)
            }

            try
            {
                categoryRepo.deleteCategory(categoryData)
                _events.emit(ListUiEvent.NotifyNotifyAfterDelete(categoryData))
            }
            catch (e: Exception) {
                val errorMessage = e.message ?: "Unknown error"
                Log.e("ListViewModel.categoryDelete", errorMessage)
                _events.emit(ListUiEvent.NotifyAboutError(errorMessage))
            }
            finally {
                _uiState.update {
                    it.copy(
                        isRemoteOperationInProgress = false,
                        data = data.copy(openedDialog = ListUiState.OpenedDialog.None)
                    )
                }
            }
        }
    }

    init {
        reset()
    }
}
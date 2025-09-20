package com.jimx.listitemselector.ui.catalog

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimx.listitemselector.data.category.CategoryRepository
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val repo: CategoryRepository
) : ViewModel() {

    val emptyAddData: CatalogUiState.AddUiData
        get() = CatalogUiState.AddUiData(false, CategoryData(0, ""))

    private val _uiState = MutableStateFlow(CatalogUiState(
        null,
        null,
        emptyAddData))
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<CatalogUiEvent>()
    val events: SharedFlow<CatalogUiEvent> = _events.asSharedFlow()

    fun reset() {
        Log.d("CatalogViewModel", "reset")

        _uiState.update {
            Log.d("CatalogViewModel.reset", "CatalogUiState.Loading")
            it.copy(
                errorMessage = null,
                data = null)
        }

        viewModelScope.launch {

            repo.loadCategories()
                .catch { e ->
                    val errorMessage = e.message ?: "Unknown error"
                    Log.e("CatalogViewModel", errorMessage)
                    _events.emit(CatalogUiEvent.NotifyAboutError(e.message))
                    _uiState.update {
                        it.copy(errorMessage = errorMessage)
                    }
                }
                .collect { items ->
                    _uiState.update {
                        Log.d("CatalogViewModel.reset", "CatalogUiState.Success")
                        it.copy(
                            errorMessage = null,
                            data = CatalogUiState.Data(items))
                    }
                }
        }

        viewModelScope.launch {
            try {
                repo.refresh()
            } catch (e: Exception) {
                Log.e("CatalogViewModel", e.message ?: "Unknown error")
                _events.emit(CatalogUiEvent.NotifyAboutError(e.message))
            }
        }
    }

    fun openAddForm()
    {
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
        _uiState.value.let {
            val addData = it.addData
            if (!addData.isOpen)
                throw Exception("Unexpected state: dialog is closed")

            _uiState.update {
                it.copy(addData = emptyAddData.copy(isOpen = false))
            }
        }
    }

    fun saveNewCategory(categoryData: CategoryData)
    {
        _uiState.value.let {
            val addData = it.addData
            if (!addData.isOpen)
                throw Exception("Unexpected state: dialog is closed")

            viewModelScope.launch {
                _uiState.update { s -> s.copy(isRemoteOperationInProgress = true) }
                try {
                    repo.addCategory(categoryData)
                    _uiState.update { s ->
                        s.copy(
                            addData = emptyAddData
                        )
                    }
                }
                catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown error"
                    Log.e("CatalogViewModel.saveNewCategory", errorMessage)
                    _events.emit(CatalogUiEvent.NotifyAboutError(errorMessage))
                }
                finally {
                    _uiState.update { s ->
                        s.copy(
                            isRemoteOperationInProgress = false
                        )
                    }
                }
            }
        }
    }

    init {
        reset()
    }
}
package com.jimx.listitemselector.ui.catalog

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimx.listitemselector.data.category.CategoryRepository
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
    savedStateHandle: SavedStateHandle,
    private val repo: CategoryRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CatalogUiState())
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<CatalogUiEvent>()
    val events: SharedFlow<CatalogUiEvent> = _events.asSharedFlow()

    fun reset() {
        Log.d("CatalogViewModel", "reset")

        viewModelScope.launch {
            repo.loadCategories()
                .catch { e ->
                    _events.emit(CatalogUiEvent.NotifyAboutError(e.message))
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
            repo.refresh()
        }
    }

    init {
        reset()
    }
}
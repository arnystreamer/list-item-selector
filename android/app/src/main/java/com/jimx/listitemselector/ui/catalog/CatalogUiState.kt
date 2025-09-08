package com.jimx.listitemselector.ui.catalog

import com.jimx.listitemselector.model.CategoryData

sealed interface CatalogUiState {
    data class Success(val items: List<CategoryData>) : CatalogUiState
    object Loading : CatalogUiState
    data class Error(val message: String) : CatalogUiState
}


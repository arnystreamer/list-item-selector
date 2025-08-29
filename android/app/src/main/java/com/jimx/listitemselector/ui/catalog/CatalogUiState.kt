package com.jimx.listitemselector.ui.catalog

import com.jimx.listitemselector.model.CategoryData

data class CatalogUiState(
    val items: List<CategoryData> = emptyList(),
    val isLoading: Boolean = false,
    val isFinishedWithError: Boolean = false
)


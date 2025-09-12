package com.jimx.listitemselector.ui.list

import com.jimx.listitemselector.model.CategoryData
import com.jimx.listitemselector.model.ItemData

sealed interface ListUiState {
    data class Success(
        val category: CategoryData? = null,
        val items: List<ItemData> = emptyList(),
        val currentSelectedItemId: Int? = null) : ListUiState

    object Loading : ListUiState
    data class Error(val message: String) : ListUiState
}
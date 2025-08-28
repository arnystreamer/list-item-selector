package com.jimx.listitemselector.ui

import com.jimx.listitemselector.model.CategoryData
import com.jimx.listitemselector.model.ItemData

data class ListUiState(
    val category: CategoryData? = null,
    val items: List<ItemData> = emptyList(),
    val currentSelectedItemId: Int? = null,
    val isLoading: Boolean = false,
    val isFinishedWithError: Boolean = false
)
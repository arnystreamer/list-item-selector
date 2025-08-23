package com.jimx.listitemselector.ui

import com.jimx.listitemselector.model.ItemData

data class ListUiState(
    val items: List<ItemData> = emptyList(),
    val currentSelectedItemId: Int? = null
)
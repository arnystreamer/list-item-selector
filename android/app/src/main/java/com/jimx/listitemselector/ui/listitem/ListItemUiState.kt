package com.jimx.listitemselector.ui.listitem

import com.jimx.listitemselector.model.ItemData

data class ListItemUiState(
    val isLoading: Boolean,
    val errorMessage: String?,
    val item: ItemData?,
    val deleteConfirmationDialog: Boolean = false,
    val editDialog: Boolean = false,
    val finishedConfirmationDialog: Boolean = false,
    var isRemoteOperationInProgress: Boolean = false
)

package com.jimx.listitemselector.ui.list

import com.jimx.listitemselector.model.CategoryData
import com.jimx.listitemselector.model.ItemData

data class ListUiState(
    val errorMessage: String?,
    val data: Data?,
    val addData: AddUiData,
    var isRemoteOperationInProgress: Boolean = false
) {
    val isLoading: Boolean
        get() = errorMessage == null && data == null

    val isError: Boolean
        get() = errorMessage != null

    val isOk: Boolean
        get() = data != null && errorMessage == null

    val isAddNew: Boolean
        get() = data != null && errorMessage == null && addData.isOpen

    data class Data(
        val category: CategoryData,
        val items: List<ItemData>,
        val chosenItem: ItemData? = null
    )

    data class AddUiData(
        val isOpen: Boolean,
        val item: ItemData
    )
}
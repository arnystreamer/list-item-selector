package com.jimx.listitemselector.ui.catalog

import com.jimx.listitemselector.model.CategoryData

data class CatalogUiState(
    val errorMessage: String?,
    val data: Data?,
    val addData: AddUiData,
    var isRemoteOperationInProgress: Boolean = false
) {
    val isLoading: Boolean
        get() = errorMessage == null && data == null

    val isError: Boolean
        get() = errorMessage != null

    val isAddNew: Boolean
        get() = data != null && errorMessage == null && addData.isOpen

    val isOk: Boolean
        get() = data != null && errorMessage == null

    data class Data(
        val items: List<CategoryData>
    )

    data class AddUiData(
        val isOpen: Boolean,
        val item: CategoryData
    )
}


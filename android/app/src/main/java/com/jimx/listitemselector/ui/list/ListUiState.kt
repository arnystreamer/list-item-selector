package com.jimx.listitemselector.ui.list

import com.jimx.listitemselector.model.CategoryData
import com.jimx.listitemselector.model.ItemData

data class ListUiState(
    val errorMessage: String?,
    val data: Data?,
    val categoryData: CategoryData?,
    val editCategoryData: EditCategoryUiData,
    val addData: AddUiData,
    var isRemoteOperationInProgress: Boolean = false
) {
    val isLoading: Boolean
        get() = errorMessage == null && data == null

    val isError: Boolean
        get() = errorMessage != null

    val isOk: Boolean
        get() = data != null && errorMessage == null

    val isEditCategory: Boolean
        get() = categoryData != null && errorMessage == null && editCategoryData.isOpen

    val isAddNew: Boolean
        get() = data != null && errorMessage == null && addData.isOpen

    data class Data(
        val items: List<ItemData>,
        val openedDialog: OpenedDialog = OpenedDialog.None,
        val chosenItem: ItemData? = null
    )

    data class AddUiData(
        val isOpen: Boolean,
        val item: ItemData
    )

    data class EditCategoryUiData(
        val isOpen: Boolean,
        val item: CategoryData
    )

    enum class OpenedDialog {
        None,
        Delete,
    }
}
package com.jimx.listitemselector.ui.listitem

import com.jimx.listitemselector.model.ItemData

data class ListItemUiState(
    val errorMessage: String?,
    val data: Data?,
    val editData: AddUiData,
    var isRemoteOperationInProgress: Boolean = false
) {
    val isLoading: Boolean
        get() = errorMessage == null && data == null

    val isError: Boolean
        get() = errorMessage != null

    val isOk: Boolean
        get() = data != null && errorMessage == null

    val isEdit: Boolean
        get() = data != null && errorMessage == null && editData.isOpen

    data class Data(
        val item: ItemData?,
        val openedDialog: OpenedDialog
    )

    data class AddUiData(
        val isOpen: Boolean,
        val item: ItemData
    )

    enum class OpenedDialog
    {
        None,
        Delete,
        Finished
    }
}

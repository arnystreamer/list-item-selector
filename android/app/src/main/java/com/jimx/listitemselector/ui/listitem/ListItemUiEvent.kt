package com.jimx.listitemselector.ui.listitem

import com.jimx.listitemselector.model.ItemData


sealed class ListItemUiEvent {
    data class NotifyAboutError(val message: String?) : ListItemUiEvent()
    data class NotifyAfterDelete(val itemData: ItemData?) : ListItemUiEvent()
    data class NotifyAfterFinish(val itemData: ItemData) : ListItemUiEvent()
}
package com.jimx.listitemselector.ui.list

import com.jimx.listitemselector.model.ItemData

sealed class ListUiEvent {
    data class NotifyAboutSelectedListItem(val item: ItemData) : ListUiEvent()
    data class NotifyAboutError(val message: String?) : ListUiEvent()
}
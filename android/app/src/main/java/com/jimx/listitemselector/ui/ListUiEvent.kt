package com.jimx.listitemselector.ui

import com.jimx.listitemselector.model.ItemData

sealed class ListUiEvent {
    data class NotifyAboutSelectedListItem(val item: ItemData) : ListUiEvent()
}
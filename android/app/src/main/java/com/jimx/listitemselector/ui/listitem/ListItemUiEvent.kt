package com.jimx.listitemselector.ui.listitem


sealed class ListItemUiEvent {
    data class NotifyAboutError(val message: String?) : ListItemUiEvent()
}
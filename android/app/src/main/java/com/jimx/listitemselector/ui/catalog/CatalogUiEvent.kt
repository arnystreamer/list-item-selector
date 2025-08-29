package com.jimx.listitemselector.ui.catalog

sealed class CatalogUiEvent {
    data class NotifyAboutError(val message: String?) : CatalogUiEvent()
}
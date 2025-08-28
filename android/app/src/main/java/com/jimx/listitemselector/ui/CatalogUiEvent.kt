package com.jimx.listitemselector.ui

import com.jimx.listitemselector.model.CategoryData

sealed class CatalogUiEvent {
    data class NotifyAboutSelectedCatalogItem(val item: CategoryData) : CatalogUiEvent()
}
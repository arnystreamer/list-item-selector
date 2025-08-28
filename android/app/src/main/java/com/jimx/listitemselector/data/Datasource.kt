package com.jimx.listitemselector.data

import com.jimx.listitemselector.model.ItemData
import com.jimx.listitemselector.model.CategoryData

class Datasource() {
    fun loadListItems(categoryId: Int): List<ItemData> {
        return listOf(
            ItemData(id = 1, name = "Item first in category $categoryId"),
            ItemData(id = 2, name = "Item second in category $categoryId"),
            ItemData(id = 3, name = "Item number three in $categoryId"),
            ItemData(id = 4, name = "Item $categoryId.4", description = "Film"),
            ItemData(id = 5, name = "Item $categoryId.5", description = "Series"),
            ItemData(id = 6, name = "Item six in category $categoryId")
        )
    }

    fun loadCategories() : List<CategoryData> {
        return listOf(
            CategoryData(id = 1, name = "Category first"),
            CategoryData(id = 2, name = "Category second"))
    }
}
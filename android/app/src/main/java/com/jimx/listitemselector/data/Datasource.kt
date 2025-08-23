package com.jimx.listitemselector.data

import com.jimx.listitemselector.model.ItemData

class Datasource() {
    fun loadItems(topicId: Int): List<ItemData> {
        return listOf(
            ItemData(id = 1, name = "Item first"),
            ItemData(id = 2, name = "Item second"),
            ItemData(id = 3, name = "Item number three"),
            ItemData(id = 4, name = "Item 4", description = "Film"),
            ItemData(id = 5, name = "Item 5", description = "Series"),
            ItemData(id = 6, name = "Item six")
        )

    }
}
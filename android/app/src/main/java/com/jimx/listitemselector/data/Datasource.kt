package com.jimx.listitemselector.data

import com.jimx.listitemselector.model.ItemData

class Datasource() {
    fun loadItems(topicId: Int): List<ItemData> {
        return listOf(
            ItemData(id = 1,),
            ItemData(id = 2,),
            ItemData(id = 3,),
            ItemData(id = 4, name = "Item 4", description = "Film"),
            ItemData(id = 5, name = "Item 5", description = "Series"),
            ItemData(id = 6,),
        )

    }
}
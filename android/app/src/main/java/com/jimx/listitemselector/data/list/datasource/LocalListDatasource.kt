package com.jimx.listitemselector.data.list.datasource

import com.jimx.listitemselector.model.ItemData
import kotlinx.coroutines.flow.Flow

interface LocalListDatasource {
    fun observeItems(categoryId: Int): Flow<List<ItemData>>
    suspend fun appendItem(categoryId: Int, item: ListEntity)
    suspend fun updateItem(categoryId: Int, itemId: Int, item: ListEntity)
    suspend fun deleteItem(categoryId: Int, itemId: Int)
    suspend fun replaceItems(categoryId: Int, newItems: List<ListEntity>)
}


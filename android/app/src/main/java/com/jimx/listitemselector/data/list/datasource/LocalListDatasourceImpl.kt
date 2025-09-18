package com.jimx.listitemselector.data.list.datasource

import android.util.Log
import com.jimx.listitemselector.model.ItemData
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class LocalListDatasourceImpl @Inject constructor() : LocalListDatasource {

    val items: MutableMap<Int, MutableStateFlow<List<ItemData>>> = mutableMapOf()

    override fun observeItems(categoryId: Int): Flow<List<ItemData>> {
        if (!items.containsKey(categoryId)) {
            items.put(categoryId, MutableStateFlow(emptyList()))
        }

        return items.getValue(categoryId)
    }

    override suspend fun appendItem(
        categoryId: Int,
        item: ListEntity
    ) {
        if (item.categoryId != categoryId)
            throw IllegalArgumentException("item.categoryId and categoryId must be equal")

        Log.d("LocalListDatasourceImpl", "appendItem: $categoryId, $item")
        if (items.containsKey(categoryId)) {
            items.getValue(categoryId).update { it +
                    ItemData(item.id, item.name, item.description) }
        }
        else
        {
            items.put(categoryId, MutableStateFlow(listOf(ItemData(item.id, item.name, item.description))))
        }
    }

    override suspend fun updateItem(
        categoryId: Int,
        itemId: Int,
        item: ListEntity
    ) {
        if (item.categoryId != categoryId)
            throw IllegalArgumentException("item.categoryId and categoryId must be equal")

        if (itemId != item.id)
            throw IllegalArgumentException("itemId and item.id must be equal")

        Log.d("LocalListDatasourceImpl", "updateItem: $categoryId, $itemId, $item")
        items.getValue(categoryId).update {
            it.map { i ->
                if (i.id == itemId) ItemData(item.id, item.name, item.description)
                else i
            }
        }
    }

    override suspend fun deleteItem(categoryId: Int, itemId: Int) {
        Log.d("LocalListDatasourceImpl", "deleteItem: $categoryId, $itemId")
        items.getValue(categoryId).update {
            it.filter { i ->
                i.id != itemId
            }
        }
    }

    override suspend fun replaceItems(
        categoryId: Int,
        newItems: List<ListEntity>
    ) {
        Log.d("LocalListDatasourceImpl", "replaceItems: $categoryId")
        if (items.containsKey(categoryId)) {
            items.getValue(categoryId).update { newItems.map { ItemData(it.id, it.name, it.description) } }
        } else {
            items.put(categoryId, MutableStateFlow(newItems.map { ItemData(it.id, it.name, it.description) }))
        }
    }
}
package com.jimx.listitemselector.data.list.datasource

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

    override suspend fun replaceItems(
        categoryId: Int,
        newItems: List<ListEntity>
    ) {
        if (items.containsKey(categoryId)) {
            items.getValue(categoryId).update { newItems.map { ItemData(it.id, it.name, it.description) } }
        } else {
            items.put(categoryId, MutableStateFlow(newItems.map { ItemData(it.id, it.name, it.description) }))
        }
    }
}
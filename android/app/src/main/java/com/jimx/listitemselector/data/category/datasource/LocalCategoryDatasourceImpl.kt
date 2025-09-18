package com.jimx.listitemselector.data.category.datasource

import android.util.Log
import com.jimx.listitemselector.model.CategoryData
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class LocalCategoryDatasourceImpl @Inject constructor() : LocalCategoryDatasource {

    private val items = MutableStateFlow<List<CategoryData>>(emptyList())

    override fun observeItems(): Flow<List<CategoryData>> = items

    override suspend fun appendItem(item: CategoryEntity) {
        Log.d("LocalCategoryDatasourceImpl", "appendItem: $item")
        items.update { it + CategoryData(item.id, item.name) }
    }

    override suspend fun updateItem(
        itemId: Int,
        item: CategoryEntity
    ) {
        if (itemId != item.id)
            throw IllegalArgumentException("itemId and item.id must be equal")

        Log.d("LocalCategoryDatasourceImpl", "updateItem: $item")
        items.update { it.map { i -> if (i.id == itemId) CategoryData(item.id, item.name) else i } }
    }

    override suspend fun deleteItem(itemId: Int) {
        Log.d("LocalCategoryDatasourceImpl", "deleteItem: $itemId")

        items.update { it.filter { i -> i.id != itemId } }
    }

    override suspend fun replaceItems(newItems: List<CategoryEntity>) {
        Log.d("LocalCategoryDatasourceImpl", "replaceItems")
        items.update { newItems.map { CategoryData(it.id, it.name) } }
    }
}
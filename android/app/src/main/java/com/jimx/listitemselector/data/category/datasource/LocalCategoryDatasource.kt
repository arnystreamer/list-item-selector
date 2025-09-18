package com.jimx.listitemselector.data.category.datasource

import com.jimx.listitemselector.model.CategoryData
import kotlinx.coroutines.flow.Flow

interface LocalCategoryDatasource {
    fun observeItems(): Flow<List<CategoryData>>
    suspend fun appendItem(item: CategoryEntity)
    suspend fun updateItem(itemId: Int, item: CategoryEntity)
    suspend fun deleteItem(itemId: Int)
    suspend fun replaceItems(newItems: List<CategoryEntity>)
}


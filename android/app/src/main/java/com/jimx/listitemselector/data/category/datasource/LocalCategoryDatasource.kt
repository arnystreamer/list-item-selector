package com.jimx.listitemselector.data.category.datasource

import kotlinx.coroutines.flow.Flow

interface LocalCategoryDatasource {
    fun observeItems(): Flow<List<CategoryDto>>
    suspend fun appendItem(item: CategoryEntity)
    suspend fun updateItem(itemId: Int, item: CategoryEntity)
    suspend fun deleteItem(itemId: Int)
    suspend fun replaceItems(newItems: List<CategoryEntity>)
}


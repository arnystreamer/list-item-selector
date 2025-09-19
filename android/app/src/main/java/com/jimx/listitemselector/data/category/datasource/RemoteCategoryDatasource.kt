package com.jimx.listitemselector.data.category.datasource

interface RemoteCategoryDatasource {
    suspend fun getAllItems(): List<CategoryDto>

    suspend fun getItem(id: Int): CategoryDto

    suspend fun addItem(item: CategoryDto): CategoryDto

    suspend fun updateItem(id: Int, item: CategoryDto): CategoryDto

    suspend fun deleteItem(id: Int): Boolean
}


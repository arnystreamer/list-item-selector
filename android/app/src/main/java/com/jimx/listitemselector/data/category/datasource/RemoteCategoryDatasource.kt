package com.jimx.listitemselector.data.category.datasource

interface RemoteCategoryDatasource {
    suspend fun fetchItems(): List<CategoryDto>
}


package com.jimx.listitemselector.data.category.datasource

import com.jimx.listitemselector.data.Datasource
import jakarta.inject.Inject

class RemoteCategoryDatasourceImpl @Inject constructor() : RemoteCategoryDatasource {
    override suspend fun fetchItems(): List<CategoryDto> {
        return Datasource().loadCategories().map { CategoryDto(it.id, it.name) }
    }
}
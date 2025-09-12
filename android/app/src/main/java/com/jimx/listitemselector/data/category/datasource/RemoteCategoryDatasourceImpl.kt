package com.jimx.listitemselector.data.category.datasource

import com.jimx.listitemselector.network.ListItemSelectorApi
import jakarta.inject.Inject

class RemoteCategoryDatasourceImpl @Inject constructor() : RemoteCategoryDatasource {
    override suspend fun fetchItems(): List<CategoryDto> {
        return ListItemSelectorApi.retrofitService.loadCategories().items
            .map { CategoryDto(it.id, it.name) }
    }
}
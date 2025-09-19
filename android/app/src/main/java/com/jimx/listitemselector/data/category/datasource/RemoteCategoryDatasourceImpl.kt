package com.jimx.listitemselector.data.category.datasource

import android.util.Log
import com.jimx.listitemselector.data.category.mapping.toApi
import com.jimx.listitemselector.data.category.mapping.toCreateApi
import com.jimx.listitemselector.data.category.mapping.toDto
import com.jimx.listitemselector.network.ListItemSelectorApi
import jakarta.inject.Inject

class RemoteCategoryDatasourceImpl @Inject constructor() : RemoteCategoryDatasource {
    override suspend fun getAllItems(): List<CategoryDto> {
        Log.d("RemoteCategoryDatasourceImpl", "getAllItems")
        return ListItemSelectorApi.listCategoriesRetrofitService.loadCategories().items
            .map { it.toDto() }
    }

    override suspend fun getItem(id: Int): CategoryDto {
        Log.d("RemoteCategoryDatasourceImpl", "getItem: $id")
        return ListItemSelectorApi.listCategoriesRetrofitService.getCategory(id)
            .item.toDto()
    }

    override suspend fun addItem(item: CategoryDto): CategoryDto {
        Log.d("RemoteCategoryDatasourceImpl", "addItem: $item")
        return ListItemSelectorApi.listCategoriesRetrofitService.postCategory(item.toCreateApi())
            .item.toDto()
    }

    override suspend fun updateItem(
        id: Int,
        item: CategoryDto
    ): CategoryDto {
        Log.d("RemoteCategoryDatasourceImpl", "updateItem: : $id, $item")
        return ListItemSelectorApi.listCategoriesRetrofitService.putCategory(id, item.toApi())
            .item.toDto()
    }

    override suspend fun deleteItem(id: Int): Boolean {
        Log.d("RemoteCategoryDatasourceImpl", "deleteItem: $id")
        ListItemSelectorApi.listCategoriesRetrofitService.deleteCategory(id)
        return true
    }
}
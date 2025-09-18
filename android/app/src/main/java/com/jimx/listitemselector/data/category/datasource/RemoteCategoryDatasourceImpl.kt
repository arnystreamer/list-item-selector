package com.jimx.listitemselector.data.category.datasource

import android.util.Log
import com.jimx.listitemselector.network.ListItemSelectorApi
import com.jimx.listitemselector.network.contract.listcategory.ListCategoryApi
import com.jimx.listitemselector.network.contract.listcategory.ListCategoryCreateApi
import jakarta.inject.Inject

class RemoteCategoryDatasourceImpl @Inject constructor() : RemoteCategoryDatasource {
    override suspend fun getAllItems(): List<CategoryDto> {
        Log.d("RemoteCategoryDatasourceImpl", "getAllItems")
        return ListItemSelectorApi.listCategoriesRetrofitService.loadCategories().items
            .map { CategoryDto(it.id, it.name) }
    }

    override suspend fun getItem(id: Int): CategoryDto {
        Log.d("RemoteCategoryDatasourceImpl", "getItem: $id")
        return ListItemSelectorApi.listCategoriesRetrofitService.getCategory(id)
            .let { CategoryDto(it.item.id, it.item.name) }
    }

    override suspend fun addItem(item: CategoryDto): CategoryDto {
        Log.d("RemoteCategoryDatasourceImpl", "addItem: $item")
        return ListItemSelectorApi.listCategoriesRetrofitService.postCategory(ListCategoryCreateApi(item.name))
            .let { CategoryDto(it.id, item.name) }
    }

    override suspend fun updateItem(
        id: Int,
        item: CategoryDto
    ): CategoryDto {
        Log.d("RemoteCategoryDatasourceImpl", "updateItem: : $id, $item")
        ListItemSelectorApi.listCategoriesRetrofitService.putCategory(id,
            ListCategoryApi(item.id, item.name))

        return CategoryDto(item.id, item.name)
    }

    override suspend fun deleteItem(id: Int): Boolean {
        Log.d("RemoteCategoryDatasourceImpl", "deleteItem: $id")
        ListItemSelectorApi.listCategoriesRetrofitService.deleteCategory(id)
        return true
    }
}
package com.jimx.listitemselector.data.category

import android.util.Log
import com.jimx.listitemselector.data.category.datasource.LocalCategoryDatasource
import com.jimx.listitemselector.data.category.datasource.RemoteCategoryDatasource
import com.jimx.listitemselector.data.category.mapping.toData
import com.jimx.listitemselector.data.category.mapping.toDto
import com.jimx.listitemselector.data.category.mapping.toEntity
import com.jimx.listitemselector.model.CategoryData
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl @Inject constructor(
    private val local: LocalCategoryDatasource,
    private val remote: RemoteCategoryDatasource
) : CategoryRepository {

    override fun loadCategories(): Flow<List<CategoryData>> {
        Log.d("CategoryRepositoryImpl", "loadCategories")
        return local.observeItems().map { it.map { i -> i.toData() } }
    }

    override suspend fun addCategory(category: CategoryData) {
        Log.d("CategoryRepositoryImpl", "addCategory: $category")
        val remoteItem = remote.addItem(category.toDto())
        local.appendItem(remoteItem.toEntity())
    }

    override suspend fun editCategory(category: CategoryData) {
        Log.d("CategoryRepositoryImpl", "editCategory: $category")
        val remoteItem = remote.updateItem(category.id, category.toDto())
        local.updateItem(category.id, remoteItem.toEntity())
    }

    override suspend fun deleteCategory(category: CategoryData) {
        Log.d("CategoryRepositoryImpl", "deleteCategory: $category")
        remote.deleteItem(category.id)
        local.deleteItem(category.id)
    }

    override suspend fun refresh() {
        Log.d("CategoryRepositoryImpl", "refresh")
        val items = remote.getAllItems()
        local.replaceItems(items.map { it.toEntity() })
    }

}


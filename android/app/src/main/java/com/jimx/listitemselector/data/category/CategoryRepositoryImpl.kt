package com.jimx.listitemselector.data.category

import android.util.Log
import com.jimx.listitemselector.data.category.datasource.CategoryDto
import com.jimx.listitemselector.data.category.datasource.CategoryEntity
import com.jimx.listitemselector.data.category.datasource.LocalCategoryDatasource
import com.jimx.listitemselector.data.category.datasource.RemoteCategoryDatasource
import com.jimx.listitemselector.model.CategoryData
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl @Inject constructor(
    private val local: LocalCategoryDatasource,
    private val remote: RemoteCategoryDatasource
) : CategoryRepository {

    override fun loadCategories(): Flow<List<CategoryData>> {
        Log.d("CategoryRepositoryImpl", "loadCategories")
        return local.observeItems()
    }

    override suspend fun addCategory(category: CategoryData) {
        Log.d("CategoryRepositoryImpl", "addCategory: $category")
        val remoteItem = remote.addItem(CategoryDto(category.id, category.name))
        local.appendItem(CategoryEntity(remoteItem.id, remoteItem.name))
    }

    override suspend fun editCategory(category: CategoryData) {
        Log.d("CategoryRepositoryImpl", "editCategory: $category")
        val remoteItem = remote.updateItem(category.id, CategoryDto(category.id, category.name))
        local.updateItem(category.id, CategoryEntity(remoteItem.id, remoteItem.name))

    }

    override suspend fun deleteCategory(category: CategoryData) {
        Log.d("CategoryRepositoryImpl", "deleteCategory: $category")
        remote.deleteItem(category.id)
        local.deleteItem(category.id)
    }

    override suspend fun refresh() {
        Log.d("CategoryRepositoryImpl", "refresh")
        val items = remote.getAllItems()
        local.replaceItems(items.map { CategoryEntity(it.id, it.name) })
    }

}


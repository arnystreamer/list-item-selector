package com.jimx.listitemselector.data.category

import com.jimx.listitemselector.model.CategoryData
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun loadCategories(): Flow<List<CategoryData>>
    suspend fun getCategory(categoryId: Int): CategoryData
    suspend fun addCategory(category: CategoryData)
    suspend fun editCategory(category: CategoryData)
    suspend fun deleteCategory(category: CategoryData)
    suspend fun refresh()
}
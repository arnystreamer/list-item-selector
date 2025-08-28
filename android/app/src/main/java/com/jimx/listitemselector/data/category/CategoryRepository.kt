package com.jimx.listitemselector.data.category

import com.jimx.listitemselector.model.CategoryData
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun loadCategories(): Flow<List<CategoryData>>
}
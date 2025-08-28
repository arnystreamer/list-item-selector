package com.jimx.listitemselector.data.category.datasource

import com.jimx.listitemselector.model.CategoryData
import kotlinx.coroutines.flow.Flow

interface LocalCategoryDatasource {
    fun observeItems(): Flow<List<CategoryData>>
}


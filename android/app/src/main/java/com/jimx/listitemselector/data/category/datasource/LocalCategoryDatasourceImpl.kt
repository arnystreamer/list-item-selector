package com.jimx.listitemselector.data.category.datasource

import com.jimx.listitemselector.model.CategoryData
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class LocalCategoryDatasourceImpl @Inject constructor() : LocalCategoryDatasource {
    override fun observeItems(): Flow<List<CategoryData>> {
        TODO("Not yet implemented")
    }
}
package com.jimx.listitemselector.data.category.datasource

import com.jimx.listitemselector.model.CategoryData
import jakarta.inject.Inject

class RemoteCategoryDatasourceImpl @Inject constructor() : RemoteCategoryDatasource {
    override suspend fun replaceItems(items: List<CategoryData>) {
        TODO("Not yet implemented")
    }
}
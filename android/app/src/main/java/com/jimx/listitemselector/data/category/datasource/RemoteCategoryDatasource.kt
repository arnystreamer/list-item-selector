package com.jimx.listitemselector.data.category.datasource

import com.jimx.listitemselector.model.CategoryData

interface RemoteCategoryDatasource {
    suspend fun replaceItems(items: List<CategoryData>)
}


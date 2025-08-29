package com.jimx.listitemselector.data.category.datasource

import com.jimx.listitemselector.model.CategoryData
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class LocalCategoryDatasourceImpl @Inject constructor() : LocalCategoryDatasource {

    private val items = MutableStateFlow<List<CategoryData>>(emptyList())

    override fun observeItems(): Flow<List<CategoryData>> = items

    override suspend fun replaceItems(newItems: List<CategoryEntity>) {
        items.update { newItems.map { CategoryData(it.id, it.name) } }
    }
}
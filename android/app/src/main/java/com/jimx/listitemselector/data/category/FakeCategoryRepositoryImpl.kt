package com.jimx.listitemselector.data.category

import com.jimx.listitemselector.model.CategoryData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeCategoryRepositoryImpl : CategoryRepository {
    private val itemsFlow = MutableStateFlow<List<CategoryData>>(emptyList())

    override fun loadCategories(): Flow<List<CategoryData>> = itemsFlow
    override suspend fun refresh() {

    }

    fun setItems(items: List<CategoryData>) {
        itemsFlow.value = items
    }
}
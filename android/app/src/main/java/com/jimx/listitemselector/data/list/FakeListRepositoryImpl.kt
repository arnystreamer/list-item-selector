package com.jimx.listitemselector.data.list

import com.jimx.listitemselector.model.ItemData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeListRepositoryImpl : ListRepository {
    private val itemsFlow = MutableStateFlow<List<ItemData>>(emptyList())

    override fun loadListItems(categoryId: Int): Flow<List<ItemData>> = itemsFlow
    override suspend fun refresh(categoryId: Int) {

    }

    fun setItems(items: List<ItemData>) {
        itemsFlow.value = items
    }
}
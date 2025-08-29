package com.jimx.listitemselector.data.list

import com.jimx.listitemselector.model.ItemData
import kotlinx.coroutines.flow.Flow

interface ListRepository {
    fun loadListItems(categoryId: Int): Flow<List<ItemData>>
    suspend fun refresh(categoryId: Int)
}
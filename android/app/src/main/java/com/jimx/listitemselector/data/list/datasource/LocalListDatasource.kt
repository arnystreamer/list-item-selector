package com.jimx.listitemselector.data.list.datasource

import com.jimx.listitemselector.model.ItemData
import kotlinx.coroutines.flow.Flow

interface LocalListDatasource {
    fun observeItems(categoryId: Int): Flow<List<ItemData>>
}


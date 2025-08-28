package com.jimx.listitemselector.data.list.datasource

import com.jimx.listitemselector.model.ItemData
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class LocalListDatasourceImpl @Inject constructor() : LocalListDatasource {
    override fun observeItems(categoryId: Int): Flow<List<ItemData>> {
        TODO("Not yet implemented")
    }
}
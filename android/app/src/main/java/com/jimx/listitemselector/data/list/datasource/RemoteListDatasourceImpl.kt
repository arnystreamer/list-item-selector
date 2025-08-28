package com.jimx.listitemselector.data.list.datasource

import com.jimx.listitemselector.model.ItemData
import jakarta.inject.Inject

class RemoteListDatasourceImpl @Inject constructor() : RemoteListDatasource {
    override suspend fun replaceItems(
        categoryId: Int,
        items: List<ItemData>
    ) {
        TODO("Not yet implemented")
    }

}
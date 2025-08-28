package com.jimx.listitemselector.data.list.datasource

import com.jimx.listitemselector.model.ItemData

interface RemoteListDatasource {
    suspend fun replaceItems(categoryId: Int, items: List<ItemData>)
}


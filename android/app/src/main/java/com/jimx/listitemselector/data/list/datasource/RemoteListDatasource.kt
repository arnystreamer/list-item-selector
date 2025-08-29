package com.jimx.listitemselector.data.list.datasource

interface RemoteListDatasource {
    suspend fun fetchItems(categoryId: Int): List<ListDto>
}


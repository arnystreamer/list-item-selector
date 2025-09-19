package com.jimx.listitemselector.data.list.datasource

interface RemoteListDatasource {
    suspend fun getAllItems(categoryId: Int): List<ListDto>

    suspend fun getItem(id: Int): ListDto

    suspend fun addItem(item: ListDto): ListDto

    suspend fun updateItem(id: Int, item: ListDto): ListDto

    suspend fun deleteItem(id: Int): Boolean
}


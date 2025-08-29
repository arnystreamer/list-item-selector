package com.jimx.listitemselector.data.list.datasource

import com.jimx.listitemselector.data.Datasource
import jakarta.inject.Inject

class RemoteListDatasourceImpl @Inject constructor() : RemoteListDatasource {
    override suspend fun fetchItems(categoryId: Int): List<ListDto> {
        return Datasource().loadListItems(categoryId).map { ListDto(it.id, it.name, it.description) }
    }
}
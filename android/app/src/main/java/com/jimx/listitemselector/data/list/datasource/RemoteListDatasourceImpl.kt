package com.jimx.listitemselector.data.list.datasource

import com.jimx.listitemselector.network.ListItemSelectorApi
import jakarta.inject.Inject

class RemoteListDatasourceImpl @Inject constructor() : RemoteListDatasource {
    override suspend fun fetchItems(categoryId: Int): List<ListDto> {

        return ListItemSelectorApi.retrofitService.loadListItems().items
            .filter { it.categoryId == categoryId }
            .map { ListDto(it.id, it.categoryId, it.name, it.description) }
    }
}
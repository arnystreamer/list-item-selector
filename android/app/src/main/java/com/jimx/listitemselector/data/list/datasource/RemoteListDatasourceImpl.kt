package com.jimx.listitemselector.data.list.datasource

import android.util.Log
import com.jimx.listitemselector.network.ListItemSelectorApi
import com.jimx.listitemselector.network.contract.listitem.ListItemApi
import com.jimx.listitemselector.network.contract.listitem.ListItemCreateApi
import jakarta.inject.Inject

class RemoteListDatasourceImpl @Inject constructor() : RemoteListDatasource {
    override suspend fun getAllItems(categoryId: Int): List<ListDto> {
        Log.d("RemoteListDatasourceImpl", "getAllItems: $categoryId")
        return ListItemSelectorApi.listItemsRetrofitService.loadListItems().items
            .filter { it.categoryId == categoryId }
            .map { ListDto(it.id, it.categoryId, it.name, it.description) }
    }

    override suspend fun getItem(id: Int): ListDto {
        Log.d("RemoteListDatasourceImpl", "getItem: $id")
        return ListItemSelectorApi.listItemsRetrofitService.getListItem(id)
            .let { ListDto(it.item.id, it.item.categoryId, it.item.name, it.item.description) }
    }

    override suspend fun addItem(item: ListDto): ListDto {
        Log.d("RemoteListDatasourceImpl", "addItem: $item")
        return ListItemSelectorApi.listItemsRetrofitService.postListItem(
            ListItemCreateApi(item.categoryId, item.name, item.description))
            .let { ListDto(it.id, item.categoryId, item.name, item.description) }
    }

    override suspend fun updateItem(
        id: Int,
        item: ListDto
    ): ListDto {
        Log.d("RemoteListDatasourceImpl", "updateItem: $id, $item")
        ListItemSelectorApi.listItemsRetrofitService.putListItem(id,
            ListItemApi(item.id, item.categoryId, item.name, item.description))

        return ListDto(item.id, item.categoryId, item.name, item.description)
    }

    override suspend fun deleteItem(id: Int): Boolean {
        Log.d("RemoteListDatasourceImpl", "deleteItem: $id")
        ListItemSelectorApi.listItemsRetrofitService.deleteListItem(id)
        return true
    }
}
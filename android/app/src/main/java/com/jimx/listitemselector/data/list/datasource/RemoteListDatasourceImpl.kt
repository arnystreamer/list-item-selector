package com.jimx.listitemselector.data.list.datasource

import android.util.Log
import com.jimx.listitemselector.data.list.mapping.toApi
import com.jimx.listitemselector.data.list.mapping.toCreateApi
import com.jimx.listitemselector.data.list.mapping.toDto
import com.jimx.listitemselector.network.ListItemSelectorApi
import jakarta.inject.Inject

class RemoteListDatasourceImpl @Inject constructor() : RemoteListDatasource {
    override suspend fun getAllItems(categoryId: Int): List<ListDto> {
        Log.d("RemoteListDatasourceImpl", "getAllItems: $categoryId")
        return ListItemSelectorApi.listItemsRetrofitService.loadListItems().items
            .filter { it.categoryId == categoryId }
            .map { it.toDto() }
    }

    override suspend fun getItem(id: Int): ListDto {
        Log.d("RemoteListDatasourceImpl", "getItem: $id")
        return ListItemSelectorApi.listItemsRetrofitService.getListItem(id)
            .item.toDto()
    }

    override suspend fun addItem(item: ListDto): ListDto {
        Log.d("RemoteListDatasourceImpl", "addItem: $item")
        return ListItemSelectorApi.listItemsRetrofitService.postListItem(item.toCreateApi())
            .item.toDto()
    }

    override suspend fun updateItem(
        id: Int,
        item: ListDto
    ): ListDto {
        Log.d("RemoteListDatasourceImpl", "updateItem: $id, $item")
        return ListItemSelectorApi.listItemsRetrofitService.putListItem(id, item.toApi())
            .item.toDto()
    }

    override suspend fun deleteItem(id: Int): Boolean {
        Log.d("RemoteListDatasourceImpl", "deleteItem: $id")
        ListItemSelectorApi.listItemsRetrofitService.deleteListItem(id)
        return true
    }
}
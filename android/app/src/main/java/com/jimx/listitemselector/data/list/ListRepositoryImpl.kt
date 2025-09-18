package com.jimx.listitemselector.data.list

import android.util.Log
import com.jimx.listitemselector.data.list.datasource.ListDto
import com.jimx.listitemselector.data.list.datasource.ListEntity
import com.jimx.listitemselector.data.list.datasource.LocalListDatasource
import com.jimx.listitemselector.data.list.datasource.RemoteListDatasource
import com.jimx.listitemselector.model.ItemData
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class ListRepositoryImpl @Inject constructor(
    private val local: LocalListDatasource,
    private val remote: RemoteListDatasource
) : ListRepository {

    override fun loadListItems(categoryId: Int): Flow<List<ItemData>> {
        Log.d("ListRepositoryImpl", "loadListItems: $categoryId")
        return local.observeItems(categoryId)
    }

    override suspend fun addListItem(categoryId: Int, listItem: ItemData) {
        Log.d("ListRepositoryImpl", "addListItem: $categoryId, $listItem")
        val remoteItem = remote.addItem(
            ListDto(listItem.id, categoryId, listItem.name, listItem.description))

        local.appendItem(remoteItem.categoryId,
            ListEntity(remoteItem.id, remoteItem.categoryId, remoteItem.name, remoteItem.description))
    }

    override suspend fun editListItem(categoryId: Int, listItem: ItemData) {
        Log.d("ListRepositoryImpl", "editListItem: $categoryId, $listItem")
        val remoteItem = remote.updateItem(listItem.id,
            ListDto(listItem.id, categoryId, listItem.name, listItem.description))

        local.updateItem(remoteItem.categoryId, remoteItem.id,
            ListEntity(remoteItem.id, remoteItem.categoryId, remoteItem.name, remoteItem.description))
    }

    override suspend fun deleteListItem(categoryId: Int, listItem: ItemData) {
        Log.d("ListRepositoryImpl", "deleteListItem: $categoryId, $listItem")
        remote.deleteItem(listItem.id)
        local.deleteItem(categoryId, listItem.id)
    }

    override suspend fun refresh(categoryId: Int) {
        Log.d("ListRepositoryImpl", "refresh: $categoryId")
        val items = remote.getAllItems(categoryId)
        local.replaceItems(
            categoryId,
            items.map { ListEntity(it.id, it.categoryId, it.name, it.description) })
    }

}


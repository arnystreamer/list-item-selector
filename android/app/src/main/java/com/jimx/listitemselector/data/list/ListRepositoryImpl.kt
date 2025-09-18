package com.jimx.listitemselector.data.list

import android.util.Log
import com.jimx.listitemselector.data.list.datasource.LocalListDatasource
import com.jimx.listitemselector.data.list.datasource.RemoteListDatasource
import com.jimx.listitemselector.data.list.mapping.toData
import com.jimx.listitemselector.data.list.mapping.toDto
import com.jimx.listitemselector.data.list.mapping.toEntity
import com.jimx.listitemselector.model.ItemData
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ListRepositoryImpl @Inject constructor(
    private val local: LocalListDatasource,
    private val remote: RemoteListDatasource
) : ListRepository {

    override fun loadListItems(categoryId: Int): Flow<List<ItemData>> {
        Log.d("ListRepositoryImpl", "loadListItems: $categoryId")
        return local.observeItems(categoryId).map {
            it.map { i -> i.toData() }
        }
    }

    override suspend fun addListItem(categoryId: Int, listItem: ItemData) {
        Log.d("ListRepositoryImpl", "addListItem: $categoryId, $listItem")

        val remoteItem = remote.addItem(
            listItem.toDto(categoryId))

        local.appendItem(
            remoteItem.categoryId,
            remoteItem.toEntity())
    }

    override suspend fun editListItem(categoryId: Int, listItem: ItemData) {
        Log.d("ListRepositoryImpl", "editListItem: $categoryId, $listItem")

        val remoteItem = remote.updateItem(listItem.id,
            listItem.toDto(categoryId))

        local.updateItem(remoteItem.categoryId, remoteItem.id,
            remoteItem.toEntity())
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
            items.map { it.toEntity() })
    }

}


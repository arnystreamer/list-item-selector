package com.jimx.listitemselector.data.list

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
        return local.observeItems(categoryId)
    }

    override suspend fun refresh(categoryId: Int) {
        val items = remote.fetchItems(categoryId)
        local.replaceItems(
            categoryId,
            items.map { ListEntity(it.id, it.name, it.description) })
    }

}


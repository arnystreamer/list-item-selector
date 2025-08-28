package com.jimx.listitemselector.data.list

import com.jimx.listitemselector.data.list.datasource.LocalListDatasource
import com.jimx.listitemselector.data.list.datasource.RemoteListDatasource
import com.jimx.listitemselector.model.ItemData
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow

class ListRepositoryImpl @Inject constructor(
    private val local: LocalListDatasource,
    private val remote: RemoteListDatasource
) : ListRepository {

    override fun loadListItems(categoryId: Int): Flow<List<ItemData>> {
        return local.observeItems(categoryId)
    }

}


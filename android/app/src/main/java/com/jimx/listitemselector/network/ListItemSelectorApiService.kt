package com.jimx.listitemselector.network

import com.jimx.listitemselector.network.contract.CollectionApi
import retrofit2.http.GET
import com.jimx.listitemselector.network.contract.ListCategoryApi
import com.jimx.listitemselector.network.contract.ListItemApi
import retrofit2.http.Path

interface ListItemSelectorApiService {
    @GET("/api/list-items/{categoryId}")
    suspend fun loadListItems(@Path("categoryId") categoryId: Int): CollectionApi<ListItemApi>

    @GET("/api/list-categories")
    suspend fun loadCategories() : CollectionApi<ListCategoryApi>
}
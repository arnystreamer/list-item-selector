package com.jimx.listitemselector.network

import com.jimx.listitemselector.network.contract.CollectionApi
import com.jimx.listitemselector.network.contract.ItemApi
import com.jimx.listitemselector.network.contract.listitem.ListItemApi
import com.jimx.listitemselector.network.contract.listitem.ListItemCreateApi
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ListItemsApiService {
    @GET("/api/list-items")
    suspend fun loadListItems(): CollectionApi<ListItemApi>

    @GET("/api/list-items/{id}")
    suspend fun getListItem(@Path("id") id: Int) : ItemApi<ListItemApi>

    @POST("/api/list-items")
    suspend fun postListItem(@Body category: ListItemCreateApi) : ItemApi<ListItemApi>

    @PUT("/api/list-items/{id}")
    suspend fun putListItem(@Path("id") id: Int, @Body category: ListItemApi): ItemApi<ListItemApi>

    @DELETE("/api/list-items/{id}")
    suspend fun deleteListItem(@Path("id") id: Int)


}
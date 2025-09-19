package com.jimx.listitemselector.network

import com.jimx.listitemselector.network.contract.CollectionApi
import com.jimx.listitemselector.network.contract.ItemApi
import retrofit2.http.GET
import com.jimx.listitemselector.network.contract.listcategory.ListCategoryApi
import com.jimx.listitemselector.network.contract.listcategory.ListCategoryCreateApi
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ListCategoriesApiService {
    @GET("/api/list-categories")
    suspend fun loadCategories(): CollectionApi<ListCategoryApi>

    @GET("/api/list-categories/{id}")
    suspend fun getCategory(@Path("id") id: Int): ItemApi<ListCategoryApi>

    @POST("/api/list-categories")
    suspend fun postCategory(@Body category: ListCategoryCreateApi): ItemApi<ListCategoryApi>

    @PUT("/api/list-categories/{id}")
    suspend fun putCategory(@Path("id") id: Int, @Body category: ListCategoryApi): ItemApi<ListCategoryApi>

    @DELETE("/api/list-categories/{id}")
    suspend fun deleteCategory(@Path("id") id: Int)
}


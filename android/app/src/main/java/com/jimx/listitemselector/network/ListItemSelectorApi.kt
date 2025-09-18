package com.jimx.listitemselector.network

import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

private const val BASE_URL =
    "https://10.0.2.2:7118"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

object ListItemSelectorApi {
    val listCategoriesRetrofitService : ListCategoriesApiService by lazy {
        retrofit.create(ListCategoriesApiService::class.java) }

    val listItemsRetrofitService : ListItemsApiService by lazy {
        retrofit.create(ListItemsApiService::class.java) }
}
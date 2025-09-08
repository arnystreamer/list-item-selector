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
    val retrofitService : ListItemSelectorApiService by lazy {
        retrofit.create(ListItemSelectorApiService::class.java) }
}
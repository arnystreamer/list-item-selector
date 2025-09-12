package com.jimx.listitemselector.network.contract

import kotlinx.serialization.Serializable

@Serializable
data class ListCategoryApi(
    val id: Int,
    val name: String
)


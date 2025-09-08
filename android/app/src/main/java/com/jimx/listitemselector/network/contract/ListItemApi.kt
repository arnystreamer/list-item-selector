package com.jimx.listitemselector.network.contract

import kotlinx.serialization.Serializable

@Serializable
data class ListItemApi(
    val id: Int,
    val name: String,
    val categoryId: Int,
    val description: String? = null
)
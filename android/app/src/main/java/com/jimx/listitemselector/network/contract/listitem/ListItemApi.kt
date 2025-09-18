package com.jimx.listitemselector.network.contract.listitem

import kotlinx.serialization.Serializable

@Serializable
data class ListItemApi(
    val id: Int,
    val categoryId: Int,
    val name: String,
    val description: String? = null
)


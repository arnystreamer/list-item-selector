package com.jimx.listitemselector.network.contract.listitem

import kotlinx.serialization.Serializable

@Serializable
data class ListItemCreateApi(
    val categoryId: Int,
    val name: String,
    val description: String?,
    val isExcluded: Boolean
)
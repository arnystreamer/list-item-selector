package com.jimx.listitemselector.network.contract.listcategory

import kotlinx.serialization.Serializable

@Serializable
data class ListCategoryApi(
    val id: Int,
    val name: String
)


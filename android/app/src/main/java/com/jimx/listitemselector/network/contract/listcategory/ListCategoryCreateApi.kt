package com.jimx.listitemselector.network.contract.listcategory

import kotlinx.serialization.Serializable

@Serializable
data class ListCategoryCreateApi(
    val name: String
)
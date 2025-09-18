package com.jimx.listitemselector.network.contract

import kotlinx.serialization.Serializable

@Serializable
data class ItemApi<T> (val item: T)


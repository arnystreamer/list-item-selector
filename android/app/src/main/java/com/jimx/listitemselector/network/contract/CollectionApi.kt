package com.jimx.listitemselector.network.contract

import kotlinx.serialization.Serializable

@Serializable
data class CollectionApi<T> (val items: List<T>)
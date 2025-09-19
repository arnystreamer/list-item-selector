package com.jimx.listitemselector.data.list.mapping

import com.jimx.listitemselector.data.list.datasource.ListDto
import com.jimx.listitemselector.network.contract.listitem.ListItemApi
import com.jimx.listitemselector.network.contract.listitem.ListItemCreateApi

fun ListItemApi.toDto() = ListDto(this.id, this.categoryId, this.name, this.description, this.isExcluded)
fun ListDto.toApi() = ListItemApi(this.id, this.categoryId, this.name, this.description, this.isExcluded)
fun ListDto.toCreateApi() = ListItemCreateApi(this.categoryId, this.name, this.description, this.isExcluded)
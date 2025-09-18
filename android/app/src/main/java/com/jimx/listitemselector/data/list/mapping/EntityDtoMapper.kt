package com.jimx.listitemselector.data.list.mapping

import com.jimx.listitemselector.data.list.datasource.ListDto
import com.jimx.listitemselector.data.list.datasource.ListEntity

fun ListEntity.toDto() = ListDto(this.id, this.categoryId, this.name, this.description, this.isExcluded)
fun ListDto.toEntity() = ListEntity(this.id, this.categoryId, this.name, this.description, this.isExcluded)
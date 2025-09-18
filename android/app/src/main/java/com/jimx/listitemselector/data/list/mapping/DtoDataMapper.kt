package com.jimx.listitemselector.data.list.mapping

import com.jimx.listitemselector.data.list.datasource.ListDto
import com.jimx.listitemselector.model.ItemData

fun ListDto.toData() = ItemData(this.id, this.name, this.description, this.isExcluded)
fun ItemData.toDto(categoryId: Int) = ListDto(this.id, categoryId, this.name, this.description, this.isExcluded)
package com.jimx.listitemselector.data.category.mapping

import com.jimx.listitemselector.data.category.datasource.CategoryDto
import com.jimx.listitemselector.model.CategoryData

fun CategoryDto.toData() = CategoryData(this.id, this.name)
fun CategoryData.toDto() = CategoryDto(this.id, this.name)
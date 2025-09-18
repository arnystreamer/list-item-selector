package com.jimx.listitemselector.data.category.mapping

import com.jimx.listitemselector.data.category.datasource.CategoryDto
import com.jimx.listitemselector.data.category.datasource.CategoryEntity

fun CategoryEntity.toDto() = CategoryDto(this.id, this.name)
fun CategoryDto.toEntity() = CategoryEntity(this.id, this.name)
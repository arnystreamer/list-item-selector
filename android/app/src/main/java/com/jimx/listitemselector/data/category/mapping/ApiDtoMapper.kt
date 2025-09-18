package com.jimx.listitemselector.data.category.mapping

import com.jimx.listitemselector.data.category.datasource.CategoryDto
import com.jimx.listitemselector.network.contract.listcategory.ListCategoryApi
import com.jimx.listitemselector.network.contract.listcategory.ListCategoryCreateApi

fun ListCategoryApi.toDto() = CategoryDto(this.id, this.name)
fun CategoryDto.toApi() = ListCategoryApi(this.id, this.name)
fun CategoryDto.toCreateApi() = ListCategoryCreateApi(this.name)





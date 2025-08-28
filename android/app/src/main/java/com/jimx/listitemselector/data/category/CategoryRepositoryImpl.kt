package com.jimx.listitemselector.data.category

import com.jimx.listitemselector.data.category.datasource.LocalCategoryDatasource
import com.jimx.listitemselector.data.category.datasource.RemoteCategoryDatasource
import com.jimx.listitemselector.model.CategoryData
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl @Inject constructor(
    private val local: LocalCategoryDatasource,
    private val remote: RemoteCategoryDatasource
) : CategoryRepository {

    override fun loadCategories(): Flow<List<CategoryData>> {
        return local.observeItems()
    }

}


package com.jimx.listitemselector

import com.jimx.listitemselector.data.category.CategoryRepository
import com.jimx.listitemselector.data.category.CategoryRepositoryImpl
import com.jimx.listitemselector.data.category.datasource.LocalCategoryDatasource
import com.jimx.listitemselector.data.category.datasource.LocalCategoryDatasourceImpl
import com.jimx.listitemselector.data.category.datasource.RemoteCategoryDatasource
import com.jimx.listitemselector.data.category.datasource.RemoteCategoryDatasourceImpl
import com.jimx.listitemselector.data.list.ListRepository
import com.jimx.listitemselector.data.list.ListRepositoryImpl
import com.jimx.listitemselector.data.list.datasource.LocalListDatasource
import com.jimx.listitemselector.data.list.datasource.LocalListDatasourceImpl
import com.jimx.listitemselector.data.list.datasource.RemoteListDatasource
import com.jimx.listitemselector.data.list.datasource.RemoteListDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ListItemSelectorModule {

    @Binds
    @Singleton
    fun bindCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    @Singleton
    fun bindListRepository(impl: ListRepositoryImpl): ListRepository

    @Binds
    @Singleton
    fun bindLocalCategoryDatasource(impl: LocalCategoryDatasourceImpl): LocalCategoryDatasource

    @Binds
    @Singleton
    fun bindRemoteCategoryDatasource(impl: RemoteCategoryDatasourceImpl): RemoteCategoryDatasource

    @Binds
    @Singleton
    fun bindLocalListDatasource(impl: LocalListDatasourceImpl): LocalListDatasource

    @Binds
    @Singleton
    fun bindRemoteListDatasource(impl: RemoteListDatasourceImpl): RemoteListDatasource
}
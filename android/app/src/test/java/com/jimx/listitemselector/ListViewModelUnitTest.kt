package com.jimx.listitemselector

import androidx.lifecycle.SavedStateHandle
import com.jimx.listitemselector.data.list.FakeListRepositoryImpl
import com.jimx.listitemselector.model.ItemData
import com.jimx.listitemselector.ui.ListViewModel
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ListViewModelUnitTest {

    val repo = FakeListRepositoryImpl()
    val savedStateHandle = SavedStateHandle(mapOf("categoryId" to 1))
    private val viewModel = ListViewModel(savedStateHandle, repo)

    @Test
    fun listViewModel_reset_currentSelectedItemIdIsNull() {
        repo.setItems(
            listOf(
                ItemData(1, "Item 1"),
                ItemData(2, "Item 2")
            )
        )
        viewModel.reset()
        assertNull(viewModel.uiState.value.currentSelectedItemId)
    }
}
package com.jimx.listitemselector

import com.jimx.listitemselector.ui.ListViewModel
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ListViewModelUnitTest {
    private val viewModel = ListViewModel()

    @Test
    fun listViewModel_reset_currentSelectedItemIdIsNull() {
        viewModel.reset()
        assertNull(viewModel.uiState.value.currentSelectedItemId)
    }
}
package com.jimx.listitemselector.services

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class SnackbarManager(private val hostState: SnackbarHostState) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    fun showMessage(
        message: String,
        actionLabel: String? = null,
        withDismissAction: Boolean = false,
        onResult: (SnackbarResult) -> Unit = {}
    ) {
        scope.launch {
            val result = hostState.showSnackbar(message, actionLabel, withDismissAction)
            onResult(result)
        }
    }
}
package com.jimx.listitemselector.services

import androidx.compose.runtime.compositionLocalOf

val LocalSnackbarManager = compositionLocalOf<SnackbarManager> {
    error("No SnackbarManager provided")
}
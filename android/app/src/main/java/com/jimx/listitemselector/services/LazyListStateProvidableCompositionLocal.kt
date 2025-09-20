package com.jimx.listitemselector.services

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.compositionLocalOf

val LocalLazyListState = compositionLocalOf<LazyListState> {
    error("No LazyListState provided")
}
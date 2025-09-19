package com.jimx.listitemselector

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import com.jimx.listitemselector.ui.theme.ListItemSelectorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(tag, "onCreate")

        enableEdgeToEdge()
        setContent {
            ListItemSelectorTheme {
                val layoutDirection = LocalLayoutDirection.current
                val startPadding = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateStartPadding(layoutDirection)
                val endPadding = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateEndPadding(layoutDirection)

                Box (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = startPadding,
                            end = endPadding)
                ) {
                    ListItemSelectorApp()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }
}



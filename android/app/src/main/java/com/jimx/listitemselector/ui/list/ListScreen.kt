package com.jimx.listitemselector.ui.list

import LoadingLayout
import android.content.Intent
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jimx.listitemselector.R
import com.jimx.listitemselector.ui.theme.ListItemSelectorTheme
import com.jimx.listitemselector.model.ItemData
import com.jimx.listitemselector.ui.common.ErrorLayout
import kotlinx.coroutines.launch

@Composable
fun ItemsList(selectedId: Int?, items: List<ItemData>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(items) { item ->
            ListItemLine(item,
                isChosen = item.id == selectedId)
        }
    }
}

@Composable
fun ListItemLine(item: ItemData, isChosen: Boolean) {
    ListItem(
        headlineContent = { Text(item.name) },
        supportingContent = {
            if (item.description != null)
                Text(item.description)
        },
        trailingContent = {
            if (isChosen) {
                Icon(
                    Icons.Filled.ThumbUp,
                    contentDescription = "Chosen",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    )
    HorizontalDivider()
}

@Composable
fun ListLayout(
    onChooseListItemClick: () -> Unit,
    items: List<ItemData>,
    selectedId: Int?,
    modifier: Modifier = Modifier
) {
    val image = painterResource(R.drawable.bg_compose_background)
    val title = stringResource(R.string.list_title_text)

    Box(modifier = modifier) {
        Column {
            Image(
                painter = image,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                alpha = 0.5f
            )
            Text(
                text = title,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                lineHeight = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
            ItemsList(selectedId, items)
        }
    }

    Box(modifier = modifier.fillMaxSize())
    {
        if (false) {
            FloatingActionButton(
                onClick = { },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            )
            {
                Icon(Icons.Filled.Add, "Add")
            }
        }

        FloatingActionButton(
            onClick = onChooseListItemClick,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        )
        {
            Icon(Icons.Filled.PlayArrow, "Choose")
        }
    }
}

@Composable
fun ListScreen(
    listViewModel: ListViewModel = viewModel()
)
{
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val uiState by listViewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        listViewModel.events.collect {
            when (it) {
                is ListUiEvent.NotifyAboutSelectedListItem ->
                    scope.launch {
                        val snackbarResult = snackbarHostState.showSnackbar(
                            message = "Selected item: ${it.item.name}",
                            actionLabel = "Share",
                            withDismissAction = true
                        )

                        when (snackbarResult) {
                            SnackbarResult.ActionPerformed -> {
                                shareChosen(context,it.item)
                            }
                            SnackbarResult.Dismissed -> {
                                // do nothing
                            }
                        }
                    }

                is ListUiEvent.NotifyAboutError ->
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = it.message ?: "Unknown error"
                        )
                    }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                snackbarHostState,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 64.dp)
            )
        },
        content = { paddingValues -> when (uiState) {
            is ListUiState.Loading -> LoadingLayout(modifier = Modifier.padding(paddingValues))
            is ListUiState.Success -> ListLayout(
                { listViewModel.choose() },
                (uiState as ListUiState.Success).items,
                (uiState as ListUiState.Success).currentSelectedItemId,
                Modifier.padding(paddingValues)
            )
            is ListUiState.Error -> ErrorLayout((uiState as ListUiState.Error).message,
                Modifier.padding(paddingValues))
            }
        })
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    ListItemSelectorTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ListScreen()
        }
    }
}

private fun shareChosen(context: Context, item: ItemData) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, "Chosen item: ${item.name}. Let's watch it!")
    }

    context.startActivity(
        Intent.createChooser(intent, context.getString(R.string.share_title))
    )
}
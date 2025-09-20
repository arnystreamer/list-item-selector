package com.jimx.listitemselector.ui.list

import LoadingLayout
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jimx.listitemselector.R
import com.jimx.listitemselector.model.CategoryData
import com.jimx.listitemselector.model.ItemData
import com.jimx.listitemselector.services.LocalLazyListState
import com.jimx.listitemselector.services.LocalNavController
import com.jimx.listitemselector.services.LocalSnackbarManager
import com.jimx.listitemselector.ui.common.ConfirmationDialog
import com.jimx.listitemselector.ui.common.ErrorLayout
import com.jimx.listitemselector.ui.listitem.ListItemAddLayout
import com.jimx.listitemselector.ui.theme.ListItemSelectorTheme

@Composable
fun ItemsList(selectedId: Int?, items: List<ItemData>, onItemClick: (ItemData) -> Unit,
              modifier: Modifier = Modifier) {

    val lazyListState = LocalLazyListState.current

    LazyColumn(modifier = modifier, state = lazyListState) {
        items(items) { item ->
            ListItemLine(
                item,
                isChosen = item.id == selectedId,
                onItemClick)
        }
    }
}

@Composable
fun ListItemLine(item: ItemData, isChosen: Boolean, onItemClick: (ItemData) -> Unit) {
    ListItem(
        headlineContent = { Text(item.name) },
        supportingContent = {
            if (item.description != null) {

                if (item.description.length > 100) {
                    Text(item.description.substring(0, 100) + "...")
                } else {
                    Text(item.description)
                }

            }
        },
        trailingContent = {
            if (isChosen) {
                Icon(
                    Icons.Filled.ThumbUp,
                    contentDescription = "Chosen",
                    modifier = Modifier.padding(8.dp)
                )
            }

            if (item.isExcluded) {
                Icon(
                    Icons.Filled.Done,
                    contentDescription = "Finished",
                    modifier = Modifier.padding(8.dp)
                )
            }
        },
        modifier = Modifier.clickable {
            onItemClick(item)
        }
    )
    HorizontalDivider()
}

@Composable
fun ListLayout(
    categoryData: CategoryData?,
    items: List<ItemData>,
    onEditCategoryClick: () -> Unit,
    onDeleteCategoryClick: () -> Unit,
    onChooseListItemClick: () -> Unit,
    onAddClick: () -> Unit,
    onItemClick: (ItemData) -> Unit,
    selectedId: Int?,
    modifier: Modifier = Modifier
) {
    var isMenuExpanded by remember { mutableStateOf(false) }

    val title = categoryData?.name ?: stringResource(R.string.list_title_text)

    Box(modifier = modifier) {
        Column {
            Row()
            {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    lineHeight = 24.sp,
                    modifier = Modifier.padding(16.dp)
                )
                Spacer(Modifier.weight(1f))
                Box(modifier = Modifier.padding(8.dp))
                {
                    IconButton(onClick = { isMenuExpanded = !isMenuExpanded }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More options")
                    }
                    DropdownMenu(
                        expanded = isMenuExpanded,
                        onDismissRequest = { isMenuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Edit") },
                            enabled = categoryData != null,
                            onClick = { onEditCategoryClick() }
                        )
                        DropdownMenuItem(
                            text = { Text("Delete") },
                            enabled = categoryData != null && items.isEmpty(),
                            onClick = { onDeleteCategoryClick() }
                        )
                    }
                }
            }
            ItemsList(selectedId, items, onItemClick)
        }
    }

    Box(modifier = modifier.fillMaxSize())
    {
        FloatingActionButton(
            onClick = onAddClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )
        {
            Icon(Icons.Filled.Add, "Add")
        }

        FloatingActionButton(
            onClick = onChooseListItemClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp, 16.dp, 102.dp, 16.dp)

        )
        {
            Icon(Icons.Filled.PlayArrow, "Choose")
        }
    }
}

@Composable
fun ListScreen(
    onListItemClick: (item: ItemData) -> Unit,
    modifier: Modifier = Modifier,
    listViewModel: ListViewModel = viewModel()
)
{
    val uiState by listViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val snackbarManager = LocalSnackbarManager.current
    val navController = LocalNavController.current

    val errorMessageMissing = stringResource(R.string.error_message_missing)

    val data = uiState.data
    val categoryData = uiState.categoryData

    if (data?.openedDialog == ListUiState.OpenedDialog.Delete && categoryData != null) {
        if (!data.items.isEmpty()) {
            throw Exception("Unexpected state: list is not empty")
        }

        ConfirmationDialog(
            onDismissRequest = { listViewModel.categoryDeleteConfirmationDialogClose() },
            onConfirmation = { listViewModel.categoryDelete() },
            isDisabled = uiState.isRemoteOperationInProgress,
            "delete",
            categoryData.name
        )
    }

    CompositionLocalProvider(
        LocalLazyListState provides rememberLazyListState()
    ) {
        val lazyListState = LocalLazyListState.current

        LaunchedEffect(Unit) {
            listViewModel.events.collect {
                when (it) {
                    is ListUiEvent.NotifyAboutSelectedListItem -> {
                        if (it.item != null) {
                            snackbarManager.showMessage(
                                message = "Selected item: ${it.item.name}",
                                actionLabel = "Share",
                                withDismissAction = true
                            ) { result ->
                                if (result == SnackbarResult.ActionPerformed) {
                                    shareChosen(context, it.item)
                                }
                            }

                            if (it.index != null) {
                                lazyListState.animateScrollToItem(it.index, -3)

                            }
                        } else {
                            snackbarManager.showMessage("No item was chosen")
                        }
                    }

                    is ListUiEvent.NotifyAboutError ->
                        snackbarManager.showMessage(
                            message = it.message ?: errorMessageMissing
                        )

                    is ListUiEvent.NotifyNotifyAfterDelete -> navController.popBackStack()
                }
            }
        }

        Box(modifier) {
            val commonModifier = modifier.fillMaxSize()

            when {
                uiState.isLoading -> LoadingLayout(modifier = commonModifier)

                uiState.isOk && !uiState.isAddNew && !uiState.isEditCategory -> ListLayout(
                    uiState.categoryData,
                    uiState.data!!.items,
                    { listViewModel.categoryEditScreenOpen() },
                    { listViewModel.categoryDeleteConfirmationDialogOpen() },
                    { listViewModel.choose() },
                    { listViewModel.openAddForm() },
                    onListItemClick,
                    uiState.data?.chosenItem?.id,
                    commonModifier
                )

                uiState.isOk && uiState.isAddNew -> ListItemAddLayout(
                    uiState.addData.item,
                    uiState.isRemoteOperationInProgress,
                    { item -> listViewModel.saveNewListItem(item) },
                    { listViewModel.dismissAddForm() },
                    commonModifier
                )

                uiState.isOk && uiState.isEditCategory -> ListAddLayout(
                    uiState.editCategoryData.item,
                    uiState.isRemoteOperationInProgress,
                    { item -> listViewModel.saveModifiedCategory(item) },
                    { listViewModel.categoryEditScreenClose() },
                    commonModifier
                )

                uiState.isError -> ErrorLayout(
                    uiState.errorMessage!!,
                    commonModifier
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ListAddLayoutPreview() {
    ListItemSelectorTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ListItemAddLayout(
                ItemData(
                    1,
                    "Orci varius",
                    "Sed vulputate eros at nibh dignissim, non scelerisque nulla auctor",
                    false
                ),
                false,
                {},
                {}
            )
        }
    }
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
            val items =  listOf(
                ItemData(1, "Orci varius", "Sed vulputate eros at nibh dignissim, non scelerisque nulla auctor", false),
                ItemData(2, "Phasellus accumsan ut metus eget laoreet interdum quam", "Proin at sapien vitae purus mollis facilisis", false),
                ItemData(3, "Nulla blandit lorem", "Donec efficitur lectus nisl", false),
                ItemData(4, "Cras non mi eu ipsum", "Vestibulum laoreet sem tristique tellus congue vulputate", false),
                ItemData(5, "Quisque facilisis arcu et libero", "Fusce eros est, elementum eu sem vel, semper accumsan nulla", true),
                ItemData(6, "Praesent ut dignissim lectus", "Fusce leo nulla, faucibus varius rutrum vitae", false),
                ItemData(7, "Aliquam at enim nibh", "Mauris pharetra pulvinar neque", false),
                ItemData(8, "Integer eleifend ligula eu quam vehicula porttitor", "Aenean porttitor urna eget consectetur ultricies", false),
                ItemData(9, "Nullam ac metus sed tortor commodo ", "Praesent auctor a justo sed faucibus", true),
                ItemData(10, "Proin suscipit risus nec diam", "Fusce eros est, elementum eu sem vel, semper accumsan nulla", false),
            )

            Scaffold(
                content = { paddingValues ->
                    ListLayout(
                        CategoryData(1, "Some name"),
                        items,
                        {},
                        {},
                        {},
                        {},
                        {},
                        null,
                        Modifier.padding(paddingValues))
                }
            )
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
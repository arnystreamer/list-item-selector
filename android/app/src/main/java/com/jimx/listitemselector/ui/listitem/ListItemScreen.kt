package com.jimx.listitemselector.ui.listitem

import LoadingLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jimx.listitemselector.R
import com.jimx.listitemselector.model.ItemData
import com.jimx.listitemselector.services.LocalNavController
import com.jimx.listitemselector.services.LocalSnackbarManager
import com.jimx.listitemselector.ui.common.ErrorLayout
import com.jimx.listitemselector.ui.common.ListAddLayout
import com.jimx.listitemselector.ui.theme.ListItemSelectorTheme

@Composable
fun DeleteConfirmationDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    isDisabled: Boolean,
    data: ItemData)
{
    Dialog(onDismissRequest = { onDismissRequest() })
    {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Text(
                text = "Are you sure you want to delete ${data.name}?",
                modifier = Modifier.padding(16.dp),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                TextButton(
                    onClick = { onDismissRequest() },
                    enabled = !isDisabled,
                    modifier = Modifier.padding(8.dp),
                ) {
                    Text("Dismiss")
                }
                TextButton(
                    onClick = { onConfirmation() },
                    enabled = !isDisabled,
                    modifier = Modifier.padding(8.dp),
                ) {
                    Text("Confirm")
                }
            }
        }
    }
}

@Composable
fun FinishConfirmationDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    isDisabled: Boolean,
    data: ItemData)
{
    Dialog(onDismissRequest = { onDismissRequest() })
    {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Text(
                text = "Are you sure you want to mark ${data.name} as finished?",
                modifier = Modifier.padding(16.dp),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                TextButton(
                    onClick = { onDismissRequest() },
                    enabled = !isDisabled,
                    modifier = Modifier.padding(8.dp),
                ) {
                    Text("Dismiss")
                }
                TextButton(
                    onClick = { onConfirmation() },
                    enabled = !isDisabled,
                    modifier = Modifier.padding(8.dp),
                ) {
                    Text("Confirm")
                }
            }
        }
    }
}

@Composable
fun ListItemLayout(
    onFinishedClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    isDisabled: Boolean,
    item: ItemData?,
    modifier: Modifier = Modifier)
{
    if (item != null)
    {
        val image = painterResource(R.drawable.bg_compose_background)

        Box(modifier) {
            Column {
                Image(
                    painter = image,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    alpha = 0.5f
                )
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    )
                    {
                        Text(
                            item.name,
                            fontSize = 20.sp
                        )
                    }
                    Text(
                        item.description ?: stringResource(R.string.no_description),
                        modifier = Modifier
                            .padding(16.dp)
                    )
                    FlowRow(modifier = Modifier.padding(16.dp).fillMaxWidth())
                    {
                        Button(
                            { onFinishedClick() },
                            enabled = !isDisabled && !item.isExcluded
                        ) {
                            Icon(Icons.Filled.Check, "Check")
                            Text("Finished")
                        }
                        Spacer(modifier = Modifier.weight(1.0f))
                        IconButton(
                            { onEditClick() },
                            enabled = !isDisabled
                        ) {
                            Icon(Icons.Filled.Edit, "Edit")
                        }
                        IconButton(
                            { onDeleteClick() },
                            enabled = !isDisabled
                        ) {
                            Icon(Icons.Filled.Delete, "Delete")
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun ListItemScreen(
    modifier: Modifier = Modifier,
    listItemViewModel: ListItemViewModel = viewModel()
)
{
    val uiState by listItemViewModel.uiState.collectAsState()
    val snackbarManager = LocalSnackbarManager.current;
    val navController = LocalNavController.current;

    val errorMessageMissing = stringResource(R.string.error_message_missing)
    LaunchedEffect(Unit) {
        listItemViewModel.events.collect {
            when (it) {
                is ListItemUiEvent.NotifyAboutError ->
                    snackbarManager.showMessage(
                        message = it.message ?: errorMessageMissing
                    )
                is ListItemUiEvent.NotifyAfterFinish -> navController.popBackStack()
                is ListItemUiEvent.NotifyAfterDelete -> navController.popBackStack()
            }
        }
    }

    val data = uiState.data;

    if (data?.openedDialog == ListItemUiState.OpenedDialog.Delete) {
        val item = data.item;

        if (item != null) {
            DeleteConfirmationDialog(
                { listItemViewModel.deleteConfirmationDialogClose() },
                { listItemViewModel.delete() },
                uiState.isRemoteOperationInProgress,
                item
            )
        }
    }

    if (data?.openedDialog == ListItemUiState.OpenedDialog.Finished) {
        val item = data.item;

        if (item != null) {
            FinishConfirmationDialog(
                { listItemViewModel.finishedConfirmationDialogClose() },
                { listItemViewModel.excludeFromChoosing() },
                uiState.isRemoteOperationInProgress,
                item
            )
        }
    }

    Box(modifier = modifier) {
        when {
            uiState.isOk && !uiState.isAddNew -> ListItemLayout(
                    { listItemViewModel.finishedConfirmationDialogOpen() },
                    { listItemViewModel.editScreenOpen() },
                    { listItemViewModel.deleteConfirmationDialogOpen() },
                    uiState.isRemoteOperationInProgress,
                    data!!.item
                )
            uiState.isOk && uiState.isAddNew -> {
                val item = data?.item;
                if (item != null) {
                    ListAddLayout(
                        item,
                        uiState.isRemoteOperationInProgress,
                        { listItemViewModel.edit(it) },
                        { listItemViewModel.editScreenClose() }
                    )
                }
            }
            uiState.isLoading -> LoadingLayout()
            uiState.isError -> ErrorLayout(uiState.errorMessage!!)

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListItemScreenPreview() {
    ListItemSelectorTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ListItemLayout(
                {},
                {},
                {},
                false,
                ItemData(
                    1,
                    "Nam ut gravida quam",
                    "Praesent ut dignissim lectus. Suspendisse gravida sapien tincidunt dignissim blandit. Praesent aliquet massa at tincidunt suscipit. Nunc condimentum nisl at dolor porta ullamcorper. In pretium pellentesque aliquam",
                    false
                )
            )
        }
    }
}
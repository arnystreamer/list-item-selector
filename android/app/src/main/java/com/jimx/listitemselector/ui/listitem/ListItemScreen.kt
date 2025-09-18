package com.jimx.listitemselector.ui.listitem

import LoadingLayout
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jimx.listitemselector.model.ItemData
import com.jimx.listitemselector.ui.common.ErrorLayout
import com.jimx.listitemselector.ui.theme.ListItemSelectorTheme
import com.jimx.listitemselector.R

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
fun ListItemLayout(
    onFinishedClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    isDisabled: Boolean,
    item: ItemData?)
{
    if (item != null)
    {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
                contentAlignment = Alignment.Center)
            {
                Text(
                    item.name,
                    fontSize = 20.sp
                )
            }
            Text(item.description ?: stringResource(R.string.no_description),
                modifier = Modifier
                    .padding(16.dp))
            FlowRow(modifier = Modifier.padding(16.dp).fillMaxWidth())
            {
                Button(
                    { onFinishedClick() },
                    enabled = !isDisabled
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

@Composable
fun ListItemScreen(
    modifier: Modifier = Modifier,
    listItemViewModel: ListItemViewModel = viewModel()
)
{
    val uiState by listItemViewModel.uiState.collectAsState()

    Log.d("ListItemScreen", "uiState: $uiState")

    if (uiState.deleteConfirmationDialog) {
        val item = uiState.item

        if (item != null) {
            DeleteConfirmationDialog(
                { listItemViewModel.deleteConfirmationDialogClose() },
                { listItemViewModel.delete() },
                uiState.isRemoteOperationInProgress,
                item
            )
        }
    }

    LaunchedEffect(Unit)
    {
        listItemViewModel.events.collect {
            when (it) {
                is ListItemUiEvent.NotifyAboutError -> Log.e("ListItemScreen", it.message ?: "Unknown error")
            }
        }
    }

    Box(modifier = modifier) {
        val item = uiState.item
        val isLoading = uiState.isLoading
        val errorMessage = uiState.errorMessage

        if (item != null) {
            ListItemLayout(
                { listItemViewModel.finishedConfirmationDialogOpen() },
                { listItemViewModel.editDialogOpen() },
                { listItemViewModel.deleteConfirmationDialogOpen() },
                uiState.isRemoteOperationInProgress,
                item
            )
        } else if (isLoading) {
            LoadingLayout()
        } else if (errorMessage != null) {
            ErrorLayout(errorMessage)
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
                    "Praesent ut dignissim lectus. Suspendisse gravida sapien tincidunt dignissim blandit. Praesent aliquet massa at tincidunt suscipit. Nunc condimentum nisl at dolor porta ullamcorper. In pretium pellentesque aliquam"
                )
            )
        }
    }
}
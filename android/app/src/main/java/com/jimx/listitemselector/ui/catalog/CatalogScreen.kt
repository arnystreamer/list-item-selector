package com.jimx.listitemselector.ui.catalog

import LoadingLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jimx.listitemselector.R
import com.jimx.listitemselector.model.CategoryData
import com.jimx.listitemselector.services.LocalSnackbarManager
import com.jimx.listitemselector.ui.common.ErrorLayout
import com.jimx.listitemselector.ui.theme.ListItemSelectorTheme

@Composable
fun CatalogAddLayout(
    item: CategoryData,
    isDisabled: Boolean,
    onSubmitClick: (CategoryData) -> Unit,
    onDismissClick: () -> Unit,
    modifier: Modifier = Modifier)
{
    var textFieldValue by remember { mutableStateOf(item.name) }

    Column(modifier = modifier)
    {
        Row(modifier = Modifier
            .fillMaxWidth())
        {
            IconButton(onClick = { onDismissClick() },
                enabled = !isDisabled,
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(Icons.Filled.Close, "Close")
            }
            Text(
                text = "Add new category",
                fontSize = 24.sp,
                lineHeight = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
        OutlinedTextField(
            textFieldValue,
            { textFieldValue = it },
            Modifier
                .padding(16.dp)
                .height(72.dp)
                .fillMaxWidth(),
            singleLine = true,
            label = { Text("Name") }
        )
        Spacer(Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            Button(
                onClick = { onSubmitClick(CategoryData(item.id, textFieldValue)) },
                enabled = !isDisabled,
                modifier = Modifier.padding(16.dp, 12.dp),
            ) {
                Text("Confirm")
            }
            OutlinedButton(
                onClick = { onDismissClick() },
                enabled = !isDisabled,
                modifier = Modifier.padding(16.dp, 12.dp),
            ) {
                Text("Dismiss")
            }
        }
    }


}

@Composable
fun CatalogLayout(
    items: List<CategoryData>,
    onAddClick: () -> Unit,
    onItemClick: (item: CategoryData) -> Unit,
    modifier: Modifier = Modifier
)
{
    val title = stringResource(R.string.category_title_text)

    Box(modifier = modifier) {
        Column {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                lineHeight = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
            LazyColumn {
                items(items) {
                    ListItem(
                        headlineContent = { Text(it.name) },
                        modifier = Modifier.clickable {
                            onItemClick(it)
                        }
                    )
                    HorizontalDivider()
                }
            }
        }
    }

    Box(modifier = modifier)
    {
        FloatingActionButton(
            onClick = { onAddClick() } ,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )
        {
            Icon(Icons.Filled.Add, "Add")
        }
    }
}

@Composable
fun CatalogScreen(
    onCatalogItemClick: (item: CategoryData) -> Unit,
    modifier: Modifier = Modifier,
    catalogViewModel: CatalogViewModel = viewModel()
) {
    val uiState by catalogViewModel.uiState.collectAsState()
    val snackbarManager = LocalSnackbarManager.current;

    val errorMessageMissing = stringResource(R.string.error_message_missing)
    LaunchedEffect(Unit) {
        catalogViewModel.events.collect {
            when (it) {
                is CatalogUiEvent.NotifyAboutError ->
                    snackbarManager.showMessage(
                        message = it.message ?: errorMessageMissing
                    )
            }
        }
    }

    Box(modifier) {
        val commonModifier = modifier.fillMaxSize()

        when {
            uiState.isLoading -> LoadingLayout(
                modifier = commonModifier
            )

            uiState.isOk && !uiState.isAddNew -> {
                CatalogLayout(
                    uiState.data!!.items,
                    { catalogViewModel.openAddForm() },
                    { data -> onCatalogItemClick(data) },
                    modifier = commonModifier
                )
            }

            uiState.isOk && uiState.isAddNew -> {
                CatalogAddLayout(
                    uiState.addData.item,
                    uiState.isRemoteOperationInProgress,
                    { data -> catalogViewModel.saveNewCategory(data) },
                    { catalogViewModel.dismissAddForm() },
                    modifier = commonModifier)
            }

            uiState.isError -> ErrorLayout(
                uiState.errorMessage!!,
                modifier = commonModifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatalogScreenPreview() {
    ListItemSelectorTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val items =  listOf(
                CategoryData(1, "Top Category"),
                CategoryData(2, "Bottom Category")
            )
            CatalogLayout(items, {}, {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatalogAddLayoutPreview() {
    ListItemSelectorTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            CatalogAddLayout(
                CategoryData(1, "Top Category"),
                false,
                {},
                {}
            )
        }
    }
}



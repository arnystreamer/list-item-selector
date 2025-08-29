package com.jimx.listitemselector.ui.catalog

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.jimx.listitemselector.ui.theme.ListItemSelectorTheme

@Composable
fun ListItemLine(item: CategoryData, onItemClick: (item: CategoryData) -> Unit) {
    ListItem(
        headlineContent = { Text(item.name) },
        modifier = Modifier.clickable {
            onItemClick(item)
        }
    )
    HorizontalDivider()
}
@Composable
fun ItemsList(items: List<CategoryData>, onItemClick: (item: CategoryData) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(items) {
            ListItemLine(it, onItemClick)
        }
    }
}

@Composable
fun CatalogLayout(
    items: List<CategoryData>,
    onItemClick: (item: CategoryData) -> Unit
)
{
    val image = painterResource(R.drawable.bg_compose_background)
    val title = stringResource(R.string.category_title_text)

    Box {
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
            ItemsList(items, onItemClick)
        }
    }
}

@Composable
fun CatalogScreen(
    onCatalogItemClick: (item: CategoryData) -> Unit,
    catalogViewModel: CatalogViewModel = viewModel()
) {
    val uiState by catalogViewModel.uiState.collectAsState()
    Log.d("CatalogScreen", "uiState: $uiState")

    CatalogLayout(uiState.items, { onCatalogItemClick(it) })
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
            CatalogScreen(onCatalogItemClick = { x -> {} })
        }
    }
}



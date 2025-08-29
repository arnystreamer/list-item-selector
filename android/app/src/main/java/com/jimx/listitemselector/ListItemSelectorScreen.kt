package com.jimx.listitemselector

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jimx.listitemselector.ui.catalog.CatalogScreen
import com.jimx.listitemselector.ui.catalog.CatalogViewModel
import com.jimx.listitemselector.ui.list.ListScreen
import com.jimx.listitemselector.ui.list.ListViewModel
import com.jimx.listitemselector.ui.theme.ListItemSelectorTheme

enum class ListItemSelectorScreen()
{
    Catalog,
    List
}

fun getNameByScreen(screen: ListItemSelectorScreen) : String {
    return when (screen) {
        ListItemSelectorScreen.Catalog -> "Catalog"
        ListItemSelectorScreen.List -> "List"
    }
}

fun getScreenByRoute(route: String?) : ListItemSelectorScreen {
    if (route == null)
        return ListItemSelectorScreen.Catalog

    if (route.startsWith(ListItemSelectorScreen.Catalog.name))
        return ListItemSelectorScreen.Catalog

    if (route.startsWith(ListItemSelectorScreen.List.name))
        return ListItemSelectorScreen.List

    return ListItemSelectorScreen.Catalog
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListItemSelectorAppBar(
        currentScreen: ListItemSelectorScreen,
        canNavigateBack: Boolean,
        navigateUp: () -> Unit,
        modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text(getNameByScreen(currentScreen)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun ListItemSelectorApp(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = getScreenByRoute(backStackEntry?.destination?.route)

    Scaffold(
        topBar = {
            ListItemSelectorAppBar(
                currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = ListItemSelectorScreen.Catalog.name,
            modifier = Modifier.padding(innerPadding)) {
                composable(route = ListItemSelectorScreen.Catalog.name
                ) { backStackEntry ->
                    val vm: CatalogViewModel = hiltViewModel(backStackEntry)
                    CatalogScreen({ catalog ->
                        navController.navigate("${ListItemSelectorScreen.List.name}/${catalog.id}")
                    },  vm)
                }
                composable(
                    route = ListItemSelectorScreen.List.name + "/{categoryId}",
                    arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val vm: ListViewModel = hiltViewModel(backStackEntry)
                        ListScreen(vm)
                }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListItemSelectorAppPreview() {
    ListItemSelectorTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ListItemSelectorApp()
        }
    }
}
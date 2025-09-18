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
import com.jimx.listitemselector.ui.listitem.ListItemScreen
import com.jimx.listitemselector.ui.listitem.ListItemViewModel
import com.jimx.listitemselector.ui.theme.ListItemSelectorTheme

enum class ApplicationScreen()
{
    Catalog,
    List,
    ListItem
}

fun getNameByScreen(screen: ApplicationScreen) : String {
    return when (screen) {
        ApplicationScreen.Catalog -> "Catalog"
        ApplicationScreen.List -> "List"
        ApplicationScreen.ListItem -> "List Item"
    }
}

fun getScreenByRoute(route: String?) : ApplicationScreen {
    if (route == null)
        return ApplicationScreen.Catalog

    if (!route.startsWith(ApplicationScreen.Catalog.name)) {
        throw Exception("Invalid route: $route")
    }

    val subroute = route.substringAfter(ApplicationScreen.Catalog.name, "")
    if (!subroute.contains(ApplicationScreen.List.name)) {
        return ApplicationScreen.Catalog
    }

    if (!subroute.contains(ApplicationScreen.ListItem.name)) {
        return ApplicationScreen.List
    }

    return ApplicationScreen.ListItem
}

fun buildRoute(categoryId: Int?, listItemId: Int?) : String {
    if (listItemId == null) {
        if (categoryId == null) {
            return ApplicationScreen.Catalog.name
        } else {
            return "${ApplicationScreen.Catalog.name}/${categoryId}/${ApplicationScreen.List.name}"
        }
    } else {
        if (categoryId == null) {
            throw Exception("categoryId is null, but listItemId is specified")
        }

        return "${ApplicationScreen.Catalog.name}/${categoryId}/${ApplicationScreen.List.name}/${listItemId}/${ApplicationScreen.ListItem.name}"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListItemSelectorAppBar(
        currentScreen: ApplicationScreen,
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
    modifier: Modifier = Modifier,
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
            startDestination = buildRoute(null, null),
            modifier = Modifier.padding(innerPadding)) {
                composable(route = buildRoute(null, null)
                ) { backStackEntry ->
                    val vm: CatalogViewModel = hiltViewModel(backStackEntry)
                    CatalogScreen(
                        { catalog ->
                            navController.navigate(buildRoute(catalog.id, null))
                        },
                        modifier,
                        vm)
                }
                composable(
                    route = "${ApplicationScreen.Catalog.name}/{categoryId}/${ApplicationScreen.List.name}",
                    arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val categoryId = backStackEntry.arguments?.getInt("categoryId")
                    if (categoryId == null) {
                        throw Exception("categoryId is null")
                    }

                    val vm: ListViewModel = hiltViewModel(backStackEntry)
                    ListScreen(
                        {},
                        { item ->
                            navController.navigate(buildRoute(categoryId, item.id))
                        },
                        modifier,
                        vm
                    )
                }
                composable(
                    route = "${ApplicationScreen.Catalog.name}/{categoryId}/${ApplicationScreen.List.name}/{itemId}/${ApplicationScreen.ListItem.name}",
                    arguments = listOf(
                        navArgument("categoryId") { type = NavType.IntType },
                        navArgument("itemId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val vm: ListItemViewModel = hiltViewModel(backStackEntry)
                    ListItemScreen(
                        modifier,
                        vm)
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
package com.rizrmdhn.restaurantapp.ui.screen.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rizrmdhn.restaurantapp.common.Helpers
import com.rizrmdhn.restaurantapp.data.Result
import com.rizrmdhn.restaurantapp.data.remote.response.RestaurantsItem
import com.rizrmdhn.restaurantapp.ui.components.RestaurantCard
import com.rizrmdhn.restaurantapp.ui.components.RestaurantCardLoader
import com.rizrmdhn.restaurantapp.ui.navigation.Screen
import com.rizrmdhn.restaurantapp.ui.theme.RestaurantAppTheme

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeScreenViewModel = viewModel(
        factory = Helpers.viewModelFactoryHelper(
            LocalContext.current
        )
    ),
    isInDarkMode: Boolean,
    onToggleDarkMode: () -> Unit,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val isSearchOpen by viewModel.isSearchOpen.collectAsState(initial = false)
    val searchQuery by viewModel.searchQuery.collectAsState(initial = "")

    viewModel.state.collectAsState(initial = Result.Loading).value.let {
        when (it) {
            is Result.Loading -> {
                viewModel.getRestaurants()

                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                ) {
                    items(10) {
                        RestaurantCardLoader()
                    }
                }
            }

            is Result.Success -> {
                HomeScreenContent(
                    restaurant = it.data,
                    navigateToDetail = { id ->
                        navController.navigate(
                            Screen.DetailRestaurant.createRoute(id)
                        )
                    },
                    onGoToHome = {
                        navController.navigate(Screen.Home.route)
                    },
                    onGoToFavorite = {
                        navController.navigate(Screen.Favorite.route)

                    },
                    isInFavoriteScreen = currentRoute == Screen.Favorite.route,
                    isInDarkMode = isInDarkMode,
                    onToggleDarkMode = onToggleDarkMode,
                    onOpenSearch = {
                        viewModel.setSearchOpen(!isSearchOpen)
                    },
                    isSearchOpen = isSearchOpen,
                    query = searchQuery,
                    onQueryChange = { query ->
                        viewModel.searchRestaurants(query)

                    },
                    onSearch = {},
                    active = false,
                    onActiveChange = { },
                    onClearQuery = {
                        viewModel.clearQuery()
                    }
                )
            }

            is Result.Error -> {
                Text(
                    text = "Error: $it",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun HomeScreenContent(
    restaurant: List<RestaurantsItem>,
    navigateToDetail: (String) -> Unit,
    onOpenSearch: () -> Unit,
    onGoToHome: () -> Unit,
    onGoToFavorite: () -> Unit,
    isInFavoriteScreen: Boolean,
    isInDarkMode: Boolean,
    onToggleDarkMode: () -> Unit,
    isSearchOpen: Boolean,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(16.dp),
    ) {
        items(restaurant, key = { it.id }) { item ->
            RestaurantCard(
                name = item.name,
                description = item.description,
                pictureId = item.pictureId,
                city = item.city,
                rating = item.rating as Double,
                onClick = {
                    navigateToDetail(item.id)
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    RestaurantAppTheme {
        HomeScreen(
            isInDarkMode = false,
            onToggleDarkMode = {},
            navController = NavHostController(
                LocalContext.current
            )
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenDarkPreview() {
    RestaurantAppTheme {
        HomeScreen(
            isInDarkMode = false,
            onToggleDarkMode = {},
            navController = NavHostController(
                LocalContext.current
            )
        )
    }
}
package com.rizrmdhn.restaurantapp.ui.screen.favorite

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import com.rizrmdhn.restaurantapp.data.local.entity.FavoriteRestaurantEntity
import com.rizrmdhn.restaurantapp.ui.components.RestaurantCard
import com.rizrmdhn.restaurantapp.ui.components.RestaurantCardLoader
import com.rizrmdhn.restaurantapp.ui.navigation.Screen
import com.rizrmdhn.restaurantapp.ui.theme.RestaurantAppTheme

@Composable
fun FavoriteScreen(
    navController: NavHostController,
    viewModel: FavoriteScreenViewModel = viewModel(
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

    viewModel.state.collectAsState(initial = Result.Loading).value.let { it ->
        when (it) {
            is Result.Loading -> {
                viewModel.getFavoriteRestaurants()

                LazyColumn(
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(10) {
                        RestaurantCardLoader()
                    }
                }
            }

            is Result.Success -> {
                FavoriteScreenContent(restaurant = it.data,
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
                        viewModel.onSearch(query)
                    },
                    onSearch = {},
                    active = false,
                    onActiveChange = {},
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
fun FavoriteScreenContent(
    restaurant: List<FavoriteRestaurantEntity>,
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

    if (restaurant.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Tidak ada favorite restaurant saat ini",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    } else {
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
                    rating = item.rating,
                    onClick = {
                        navigateToDetail(item.id)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteScreenPreview() {
    RestaurantAppTheme {
        FavoriteScreen(
            navController = NavHostController(LocalContext.current),
            isInDarkMode = false,
            onToggleDarkMode = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FavoriteScreenContentPreview() {
    RestaurantAppTheme {
        FavoriteScreen(
            navController = NavHostController(LocalContext.current),
            isInDarkMode = false,
            onToggleDarkMode = {}
        )
    }
}


package com.rizrmdhn.restaurantapp.ui.screen.favorite

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rizrmdhn.restaurantapp.common.Helpers
import com.rizrmdhn.restaurantapp.data.Result
import com.rizrmdhn.restaurantapp.data.local.entity.FavoriteRestaurantEntity
import com.rizrmdhn.restaurantapp.ui.components.ErrorText
import com.rizrmdhn.restaurantapp.ui.components.RestaurantCard
import com.rizrmdhn.restaurantapp.ui.components.RestaurantCardLoader
import com.rizrmdhn.restaurantapp.ui.components.TopBar
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

    viewModel.state.collectAsState(initial = Result.Loading).value.let { state ->
        when (state) {
            is Result.Loading -> {
                viewModel.getFavoriteRestaurants()

                Scaffold(topBar = {
                    TopBar(
                        onOpenSearch = {},
                        onGoToHome = {
                            navController.navigate(Screen.Home.route)
                        },
                        onGoToFavorite = {
                            navController.navigate(Screen.Favorite.route)

                        },
                        onGoToAbout = {
                            navController.navigate(Screen.About.route)
                        },
                        isInFavoriteScreen = currentRoute == Screen.Favorite.route,
                        isInDarkMode = isInDarkMode,
                        onToggleDarkMode = onToggleDarkMode,
                        isSearchOpen = isSearchOpen,
                        query = searchQuery,
                        searchPlaceHolder = "Cari favorite restaurant...",
                        onQueryChange = {},
                        onSearch = {},
                        active = false,
                        onActiveChange = {},
                        onClearQuery = {}
                    )
                }
                ) { innerPadding ->
                    LazyColumn(
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        items(10) {
                            RestaurantCardLoader()
                        }
                    }
                }
            }

            is Result.Success -> {
                FavoriteScreenContent(restaurant = state.data,
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
                    onGoToAbout = {
                        navController.navigate(Screen.About.route)
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
                        viewModel.searchFavoriteRestaurants(query)
                    },
                    onSearch = {},
                    active = false,
                    onActiveChange = {},
                    onClearQuery = {
                        viewModel.clearQuery()
                    })
            }

            is Result.Error -> {
                Scaffold(
                    topBar = {
                        TopBar(
                            onOpenSearch = {},
                            onGoToHome = {
                                navController.navigate(Screen.Home.route)
                            },
                            onGoToFavorite = {
                                navController.navigate(Screen.Favorite.route)

                            },
                            onGoToAbout = {
                                navController.navigate(Screen.About.route)
                            },
                            isInFavoriteScreen = currentRoute == Screen.Favorite.route,
                            isInDarkMode = isInDarkMode,
                            onToggleDarkMode = onToggleDarkMode,
                            isSearchOpen = isSearchOpen,
                            query = "",
                            searchPlaceHolder = "Cari restaurant...",
                            onQueryChange = {},
                            onSearch = {},
                            active = false,
                            onActiveChange = {},
                            onClearQuery = {}
                        )
                    }
                ) { innerPadding ->
                    ErrorText(
                        state.errorMessage,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
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
    onGoToAbout: () -> Unit,
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

    Scaffold(topBar = {
        TopBar(
            onOpenSearch = onOpenSearch,
            onGoToHome = onGoToHome,
            onGoToFavorite = onGoToFavorite,
            onGoToAbout = onGoToAbout,
            isInFavoriteScreen = isInFavoriteScreen,
            isInDarkMode = isInDarkMode,
            onToggleDarkMode = onToggleDarkMode,
            isSearchOpen = isSearchOpen,
            query = query,
            searchPlaceHolder = "Cari favorite restaurant...",
            onQueryChange = onQueryChange,
            onSearch = onSearch,
            active = active,
            onActiveChange = onActiveChange,
            onClearQuery = onClearQuery
        )
    }) { innerPadding ->
        if (restaurant.isEmpty()) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding),
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
                state = listState, modifier = modifier.padding(innerPadding)
            ) {
                items(restaurant, key = { it.id }) { item ->
                    RestaurantCard(name = item.name,
                        description = item.description,
                        pictureId = item.pictureId,
                        city = item.city,
                        rating = item.rating,
                        onClick = {
                            navigateToDetail(item.id)
                        })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteScreenPreview() {
    RestaurantAppTheme {
        FavoriteScreen(navController = NavHostController(LocalContext.current),
            isInDarkMode = false,
            onToggleDarkMode = {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FavoriteScreenContentPreview() {
    RestaurantAppTheme {
        FavoriteScreen(navController = NavHostController(LocalContext.current),
            isInDarkMode = false,
            onToggleDarkMode = {})
    }
}


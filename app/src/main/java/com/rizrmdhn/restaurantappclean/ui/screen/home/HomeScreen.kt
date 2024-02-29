package com.rizrmdhn.restaurantappclean.ui.screen.home

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rizrmdhn.core.data.Resource
import com.rizrmdhn.core.domain.model.Restaurant
import com.rizrmdhn.restaurantapp.ui.navigation.Screen
import com.rizrmdhn.restaurantappclean.ui.components.RestaurantCard
import com.rizrmdhn.restaurantappclean.ui.components.RestaurantCardLoader
import com.rizrmdhn.restaurantappclean.ui.components.TopBar
import com.rizrmdhn.restaurantappclean.ui.theme.RestaurantAppCleanTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeScreenViewModel = koinViewModel(),
    isInDarkMode: Boolean,
    onToggleDarkMode: () -> Unit,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val isSearchOpen by viewModel.isSearchOpen.collectAsState()
    val query by viewModel.query.collectAsState()

    // Navigation Function
    val navigateToHome = {
        navController.navigate(Screen.Home.route)
    }

    val navigateToFavorite = {
        navController.navigate(Screen.Favorite.route)
    }

    val navigateToAbout = {
        navController.navigate(Screen.About.route)
    }

    // Function
    val onToggleSearch = {
        viewModel.setSearchOpen(!isSearchOpen)
    }

    val onQueryChange = { newQuery: String ->
        viewModel.onQueryChange(newQuery)
    }

    fun onSearch() {
        viewModel.onSearch()
    }


    val onClearQuery = {
        viewModel.clearQuery()
    }

    viewModel.state.collectAsState().value.let { result ->
        when (result) {
            is Resource.Loading -> {
                viewModel.getRestaurants()

                Scaffold(topBar = {
                    TopBar(
                        onOpenSearch = onToggleSearch,
                        onGoToHome = navigateToHome,
                        onGoToFavorite = navigateToFavorite,
                        onGoToAbout = navigateToAbout,
                        isInFavoriteScreen = currentRoute == Screen.Favorite.route,
                        isInDarkMode = isInDarkMode,
                        onToggleDarkMode = onToggleDarkMode,
                        isSearchOpen = isSearchOpen,
                        query = query,
                        searchPlaceHolder = "Cari restaurant...",
                        onQueryChange = {
                            onQueryChange(it)
                        },
                        onSearch = {},
                        active = false,
                        onActiveChange = {},
                        onClearQuery = onClearQuery
                    )
                }) { innerPadding ->
                    LazyColumn(
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        items(10) {
                            RestaurantCardLoader()
                        }
                    }
                }
            }

            is Resource.Success -> {
                result.data?.let {
                    HomeScreenContent(
                        restaurant = it,
                        navigateToDetail = { id ->
                            navController.navigate(
                                Screen.DetailRestaurant.createRoute(id)
                            )
                        },
                        onOpenSearch = onToggleSearch,
                        onGoToHome = navigateToHome,
                        onGoToFavorite = navigateToFavorite,
                        onGoToAbout = navigateToAbout,
                        isInFavoriteScreen = currentRoute == Screen.Favorite.route,
                        isInDarkMode = isInDarkMode,
                        onToggleDarkMode = onToggleDarkMode,
                        isSearchOpen = isSearchOpen,
                        query = query,
                        onQueryChange = {
                            onQueryChange(it)
                        },
                        onSearch = { _ ->
                            onSearch()
                        },
                        active = false,
                        onActiveChange = {},
                        onClearQuery = onClearQuery
                    )
                }
            }

            is Resource.Error -> {
                Scaffold(topBar = {
                    TopBar(
                        onOpenSearch = onToggleSearch,
                        onGoToHome = navigateToHome,
                        onGoToFavorite = navigateToFavorite,
                        onGoToAbout = navigateToAbout,
                        isInFavoriteScreen = currentRoute == Screen.Favorite.route,
                        isInDarkMode = isInDarkMode,
                        onToggleDarkMode = onToggleDarkMode,
                        isSearchOpen = isSearchOpen,
                        query = query,
                        searchPlaceHolder = "Cari restaurant...",
                        onQueryChange = {

                        },
                        onSearch = {},
                        active = false,
                        onActiveChange = {},
                        onClearQuery = onClearQuery
                    )
                }) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Terjadi kesalahan saat mengambil data: ${result.message}",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun HomeScreenContent(
    restaurant: List<Restaurant>,
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
            searchPlaceHolder = "Cari restaurant...",
            onQueryChange = onQueryChange,
            onSearch = onSearch,
            active = active,
            onActiveChange = onActiveChange,
            onClearQuery = onClearQuery
        )
    }) { innerPadding ->
        if (restaurant.isEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Text(
                    text = "Tidak ada restaurant",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                state = listState, modifier = modifier.padding(innerPadding)
            ) {
                items(restaurant, key = { it.id }) { restaurant ->
                    RestaurantCard(name = restaurant.name,
                        description = restaurant.description,
                        pictureId = restaurant.pictureId,
                        city = restaurant.city,
                        rating = restaurant.rating,
                        onClick = {
                            navigateToDetail(restaurant.id)
                        })
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    RestaurantAppCleanTheme {
        HomeScreen(
            isInDarkMode = false, onToggleDarkMode = {}, navController = NavHostController(
                LocalContext.current
            )
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenDarkPreview() {
    RestaurantAppCleanTheme {
        HomeScreen(
            isInDarkMode = false, onToggleDarkMode = {}, navController = NavHostController(
                LocalContext.current
            )
        )
    }
}
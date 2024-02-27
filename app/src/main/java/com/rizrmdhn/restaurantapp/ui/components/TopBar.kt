package com.rizrmdhn.restaurantapp.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.rizrmdhn.restaurantapp.R
import com.rizrmdhn.restaurantapp.ui.theme.RestaurantAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onOpenSearch: () -> Unit,
    onGoToHome: () -> Unit,
    onGoToFavorite: () -> Unit,
    onGoToAbout: () -> Unit,
    isInFavoriteScreen: Boolean,
    isInDarkMode: Boolean,
    onToggleDarkMode: () -> Unit,
    isSearchOpen: Boolean,
    query: String,
    searchPlaceHolder: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
) {
    TopAppBar(
        title = {
            if (!isSearchOpen) {
                Text(
                    text = "Restaurant App",
                    color = Color(0xFFEEEEEE),
                )
            }
        },
        actions = {
           if (!isSearchOpen) {
               IconButton(
                   onClick = onOpenSearch
               ) {
                   Icon(
                       imageVector = Icons.Default.Search,
                       contentDescription = "search_button",
                   )
               }
               if (isInFavoriteScreen) {
                   IconButton(
                       onClick = onGoToHome
                   ) {
                       Icon(
                           imageVector = Icons.Default.Home,
                           contentDescription = "home_page",
                       )
                   }
               } else {
                   IconButton(
                       onClick = onGoToFavorite
                   ) {
                       Icon(
                           imageVector = Icons.Default.Favorite,
                           contentDescription = "favorite_page",
                       )
                   }
               }
               IconButton(
                   onClick = onGoToAbout
               ) {
                   Icon(
                       imageVector = Icons.Default.Info,
                       contentDescription = "about_page"
                   )
               }
               IconButton(
                   onClick = onToggleDarkMode
               ) {
                   Icon(
                       painter = painterResource(
                           id = if (isInDarkMode) {
                               R.drawable.baseline_dark_mode_24
                           } else {
                               R.drawable.baseline_sunny_24
                           }
                       ),
                       contentDescription = "dark_mode_toggle",
                   )
               }
           } else {
               Search(
                     query = query,
                     searchPlaceHolder = searchPlaceHolder,
                     onQueryChange = onQueryChange,
                     onSearch = onSearch,
                     active = active,
                     onActiveChange = onActiveChange,
                     onClearQuery = onClearQuery,
               )
           }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF393E46),
            titleContentColor = Color(0xFFEEEEEE),
            actionIconContentColor = Color(0xFFEEEEEE),
            navigationIconContentColor = Color(0xFFEEEEEE),
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    RestaurantAppTheme {
        TopBar(
            onOpenSearch = {},
            onGoToHome = {},
            onGoToFavorite = {},
            onGoToAbout = {},
            isInFavoriteScreen = false,
            onToggleDarkMode = {},
            isInDarkMode = false,
            isSearchOpen = true,
            query = "",
            searchPlaceHolder = "Cari favorite user...",
            onQueryChange = {},
            onSearch = {},
            active = false,
            onActiveChange = {},
            onClearQuery = {},
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TopBarDarkPreview() {
    RestaurantAppTheme {
        TopBar(
            onOpenSearch = {},
            onGoToHome = {},
            onGoToFavorite = {},
            onGoToAbout = {},
            isInFavoriteScreen = true,
            onToggleDarkMode = {},
            isInDarkMode = true,
            isSearchOpen = false,
            query = "",
            searchPlaceHolder = "Cari favorite user...",
            onQueryChange = {},
            onSearch = {},
            active = false,
            onActiveChange = {},
            onClearQuery = {},
        )
    }
}


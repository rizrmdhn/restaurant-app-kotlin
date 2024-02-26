package com.example.restaurantapp.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.restaurantapp.R
import com.example.restaurantapp.ui.theme.RestaurantAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onGoToSearch: () -> Unit,
    onGoToFavorite: () -> Unit,
    isInDarkMode: Boolean,
    onToggleDarkMode: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = "Restaurant App"
            )
        },
        actions = {
            IconButton(
                onClick = onGoToSearch
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                )
            }
            IconButton(
                onClick = onGoToFavorite
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite",
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
                    contentDescription = "Dark Mode",
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
            onGoToSearch = {},
            onGoToFavorite = {},
            onToggleDarkMode = {},
            isInDarkMode = false,
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TopBarDarkPreview() {
    RestaurantAppTheme {
        TopBar(
            onGoToSearch = {},
            onGoToFavorite = {},
            onToggleDarkMode = {},
            isInDarkMode = true,
        )
    }
}


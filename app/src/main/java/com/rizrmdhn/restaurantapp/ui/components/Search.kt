package com.rizrmdhn.restaurantapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rizrmdhn.restaurantapp.ui.theme.RestaurantAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    query: String,
    searchPlaceHolder: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = active,
        onActiveChange = onActiveChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Color(0xFFEEEEEE)
            )
        },
        placeholder = {
            Text(
                text = searchPlaceHolder,
                color = Color(0xFFEEEEEE)
            )
        },
        trailingIcon = {
           IconButton(
               onClick = onClearQuery
           ) {
               Icon(
                   imageVector = Icons.Default.Clear,
                   contentDescription = null,
                   tint = Color(0xFFEEEEEE)
               )
           }
        },
        colors = SearchBarDefaults.colors(
            containerColor = Color(0xFF393E46),
            inputFieldColors = TextFieldDefaults.colors(
                focusedTextColor = Color(0xFFEEEEEE),
                focusedContainerColor = Color(0xFFEEEEEE),
                unfocusedContainerColor = Color(0xFFEEEEEE),
                disabledContainerColor = Color(0xFFEEEEEE),
            )
        ),
        content = {},
        modifier = modifier
            .padding(16.dp)
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RestaurantAppTheme {
        Search(
            query = "",
            searchPlaceHolder = "Cari restaurant...",
            onQueryChange = {},
            onSearch = {},
            active = false,
            onActiveChange = {},
            onClearQuery = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DarkPreview() {
    RestaurantAppTheme {
        Search(
            query = "",
            searchPlaceHolder = "Cari restaurant...",
            onQueryChange = {},
            onSearch = {},
            active = false,
            onActiveChange = {},
            onClearQuery = {}
        )
    }
}
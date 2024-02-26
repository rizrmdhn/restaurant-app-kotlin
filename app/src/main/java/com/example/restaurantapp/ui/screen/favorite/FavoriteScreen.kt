package com.example.restaurantapp.ui.screen.favorite

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.restaurantapp.common.Helpers
import com.example.restaurantapp.data.Result
import com.example.restaurantapp.data.local.entity.FavoriteRestaurantEntity
import com.example.restaurantapp.ui.components.RestaurantCard
import com.example.restaurantapp.ui.components.RestaurantCardLoader
import com.example.restaurantapp.ui.theme.RestaurantAppTheme

@Composable
fun FavoriteScreen(
    viewModel: FavoriteScreenViewModel = viewModel(
        factory = Helpers.viewModelFactoryHelper(
            LocalContext.current
        )
    )
) {
    viewModel.state.collectAsState(initial = Result.Loading).value.let {
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
                FavoriteScreenContent(
                    restaurant = it.data
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
    restaurant: List<FavoriteRestaurantEntity>
) {
    val listState = rememberLazyListState()

    if (restaurant.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
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
            contentPadding = PaddingValues(16.dp)
        ) {
            items(restaurant, key = { it.id }) { item ->
                RestaurantCard(
                    id = item.id,
                    name = item.name,
                    description = item.description,
                    pictureId = item.pictureId,
                    city = item.city,
                    rating = item.rating,
                    onClick = { /*TODO*/ }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteScreenPreview() {
    RestaurantAppTheme {
        FavoriteScreen()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FavoriteScreenContentPreview() {
    RestaurantAppTheme {
        FavoriteScreen()
    }
}


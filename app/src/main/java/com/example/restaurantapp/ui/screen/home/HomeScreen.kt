package com.example.restaurantapp.ui.screen.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.restaurantapp.common.Helpers
import com.example.restaurantapp.data.Result
import com.example.restaurantapp.data.remote.response.RestaurantsItem
import com.example.restaurantapp.ui.components.RestaurantCard
import com.example.restaurantapp.ui.components.RestaurantCardLoader
import com.example.restaurantapp.ui.theme.RestaurantAppTheme

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = viewModel(
        factory = Helpers.viewModelFactoryHelper(
            LocalContext.current
        )
    ),
    navigateToDetail: (String) -> Unit
) {
    viewModel.state.collectAsState(initial = Result.Loading).value.let {
        when (it) {
            is Result.Loading -> {
                viewModel.getRestaurants()

                LazyColumn(
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(10) {
                        RestaurantCardLoader()
                    }
                }
            }

            is Result.Success -> {
                HomeScreenContent(
                    restaurant = it.data,
                    navigateToDetail = navigateToDetail
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
    navigateToDetail: (String) -> Unit
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(16.dp),
    ) {
        items(restaurant, key = { it.id }) { item ->
            RestaurantCard(
                id = item.id,
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
            navigateToDetail = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenDarkPreview() {
    RestaurantAppTheme {
        HomeScreen(
            navigateToDetail = {}
        )
    }
}
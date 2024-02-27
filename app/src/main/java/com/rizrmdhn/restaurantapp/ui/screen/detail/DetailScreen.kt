package com.rizrmdhn.restaurantapp.ui.screen.detail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.rizrmdhn.restaurantapp.R
import com.rizrmdhn.restaurantapp.common.Helpers
import com.rizrmdhn.restaurantapp.data.Result
import com.rizrmdhn.restaurantapp.data.local.entity.FavoriteRestaurantEntity
import com.rizrmdhn.restaurantapp.data.remote.response.CategoriesItem
import com.rizrmdhn.restaurantapp.data.remote.response.CustomerReviewsItem
import com.rizrmdhn.restaurantapp.data.remote.response.DrinksItem
import com.rizrmdhn.restaurantapp.data.remote.response.FoodsItem
import com.rizrmdhn.restaurantapp.data.remote.response.Menus
import com.rizrmdhn.restaurantapp.data.remote.response.Restaurant
import com.rizrmdhn.restaurantapp.ui.components.DetailRestaurantLoader
import com.rizrmdhn.restaurantapp.ui.components.ErrorText
import com.rizrmdhn.restaurantapp.ui.components.RestaurantCategories
import com.rizrmdhn.restaurantapp.ui.components.RestaurantCustomerReviews
import com.rizrmdhn.restaurantapp.ui.components.RestaurantDrinks
import com.rizrmdhn.restaurantapp.ui.components.RestaurantFoods
import com.rizrmdhn.restaurantapp.ui.components.shimmerBrush
import com.rizrmdhn.restaurantapp.ui.theme.RestaurantAppTheme

@Composable
fun DetailScreen(
    restaurantId: String, viewModel: DetailScreenViewModel = viewModel(
        factory = Helpers.viewModelFactoryHelper(
            LocalContext.current
        )
    ), navigateBack: () -> Unit, isInDarkMode: Boolean, onToggleDarkMode: () -> Unit
) {
    val isFavorite by viewModel.isFavoriteRestaurant.collectAsState(initial = false)

    viewModel.state.collectAsState(initial = Result.Loading).value.let { state ->
        when (state) {
            is Result.Loading -> {
                viewModel.getDetailRestaurant(restaurantId)
                viewModel.isFavoriteRestaurant(restaurantId)

                DetailRestaurantLoader()
            }

            is Result.Success -> {
                DetailScreenContent(
                    restaurantDetail = state.data,
                    navigateBack = navigateBack,
                    isInDarkMode = isInDarkMode,
                    onToggleDarkMode = onToggleDarkMode,
                    onSaveToFavorite = viewModel::insertFavoriteRestaurant,
                    isFavorite = isFavorite,
                )
            }

            is Result.Error -> {
                ErrorText(
                    state.errorMessage
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenContent(
    restaurantDetail: Restaurant,
    navigateBack: () -> Unit,
    isInDarkMode: Boolean,
    onToggleDarkMode: () -> Unit,
    onSaveToFavorite: (FavoriteRestaurantEntity) -> Unit,
    isFavorite: Boolean,
    modifier: Modifier = Modifier
) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = restaurantDetail.name, maxLines = 1, overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = navigateBack
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = "Back"
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    onSaveToFavorite(
                        FavoriteRestaurantEntity(
                            id = restaurantDetail.id,
                            name = restaurantDetail.name,
                            description = restaurantDetail.description,
                            pictureId = restaurantDetail.pictureId,
                            city = restaurantDetail.city,
                            rating = restaurantDetail.rating as Double,
                        )
                    )
                }) {
                    Icon(
                        painter = if (isFavorite) {
                            painterResource(
                                R.drawable.baseline_bookmark_24
                            )
                        } else {
                            painterResource(
                                R.drawable.baseline_bookmark_border_24
                            )
                        }, contentDescription = "Favorite"
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
    }) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            SubcomposeAsyncImage(
                model = Helpers.mediumRestaurantImage(restaurantDetail.pictureId),
                contentDescription = restaurantDetail.name,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(
                        min = 150.dp, max = 200.dp
                    )
            ) {
                val state = painter.state
                if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                    Box(
                        modifier = Modifier
                            .background(
                                shimmerBrush(
                                    targetValue = 1300f, showShimmer = true
                                )
                            )
                            .size(64.dp)
                            .clip(CircleShape)
                    )
                } else {
                    SubcomposeAsyncImageContent()
                }
            }
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = restaurantDetail.name,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                Text(
                    text = restaurantDetail.address,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                Row {
                    Text(
                        text = restaurantDetail.city,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(
                        modifier = Modifier.width(16.dp)
                    )
                    Text(
                        text = "Rating: ${restaurantDetail.rating}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                Text(
                    text = restaurantDetail.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                Text(
                    text = "Categories",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                LazyRow(
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    items(restaurantDetail.categories, key = { it.name }) { category ->
                        Row {
                            RestaurantCategories(
                                category.name
                            )
                            Spacer(
                                modifier = Modifier.width(8.dp)
                            )
                        }
                    }
                }
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                Text(
                    text = "Menus",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                LazyRow(
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    items(restaurantDetail.menus.foods, key = { it.name }) { menu ->
                        Row {
                            RestaurantFoods(
                                menu.name
                            )
                            Spacer(
                                modifier = Modifier.width(8.dp)
                            )
                        }
                    }
                }
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                Text(
                    text = "Drinks",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                LazyRow(
                    modifier = Modifier
                        .padding(start = 16.dp)
                ) {
                    items(restaurantDetail.menus.drinks, key = { it.name }) { menu ->
                        Row {
                            RestaurantDrinks(
                                menu.name
                            )
                            Spacer(
                                modifier = Modifier.width(8.dp)
                            )
                        }
                    }
                }
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                Text(
                    text = "Customer Reviews",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                LazyRow(
                    modifier = modifier.padding(start = 16.dp)
                ) {
                    items(restaurantDetail.customerReviews.size) {index ->
                        Row {
                            RestaurantCustomerReviews(
                                name =  restaurantDetail.customerReviews[index].name,
                                review = restaurantDetail.customerReviews[index].review,
                                date =  restaurantDetail.customerReviews[index].date,
                            )
                            Spacer(
                                modifier = Modifier.width(8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenContentPreview() {
    RestaurantAppTheme {
        DetailScreenContent(
            restaurantDetail = Restaurant(
                id = "restaurantId",
                name = "Restaurant Name",
                description = "Restaurant Description",
                pictureId = "restaurantPictureId",
                city = "Restaurant City",
                rating = 4.5,
                address = "Restaurant Address",
                categories = listOf(
                    CategoriesItem(
                        name = "Category Name"
                    )
                ),
                customerReviews = listOf(
                    CustomerReviewsItem(
                        name = "Customer Name", review = "Customer Review", date = "Customer Date"
                    )
                ),
                menus = Menus(
                    foods = listOf(
                        FoodsItem(
                            name = "Food Name",
                        )
                    ), drinks = listOf(
                        DrinksItem(
                            name = "Drink Name",
                        )
                    )
                )
            ),
            navigateBack = {},
            isInDarkMode = false,
            onToggleDarkMode = {},
            onSaveToFavorite = {},
            isFavorite = true
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailScreenContentDarkPreview() {
    RestaurantAppTheme {
        DetailScreenContent(
            restaurantDetail = Restaurant(
                id = "restaurantId",
                name = "Restaurant Name",
                description = "Restaurant Description",
                pictureId = "restaurantPictureId",
                city = "Restaurant City",
                rating = 4.5,
                address = "Restaurant Address",
                categories = listOf(
                    CategoriesItem(
                        name = "Category Name"
                    )
                ),
                customerReviews = listOf(
                    CustomerReviewsItem(
                        name = "Customer Name", review = "Customer Review", date = "Customer Date"
                    )
                ),
                menus = Menus(
                    foods = listOf(
                        FoodsItem(
                            name = "Food Name",
                        )
                    ), drinks = listOf(
                        DrinksItem(
                            name = "Drink Name",
                        )
                    )
                )
            ),
            navigateBack = {},
            isInDarkMode = true,
            onToggleDarkMode = {},
            onSaveToFavorite = {},
            isFavorite = false
        )
    }
}
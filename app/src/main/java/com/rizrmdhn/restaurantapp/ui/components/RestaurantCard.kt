package com.rizrmdhn.restaurantapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.rizrmdhn.restaurantapp.common.Helpers
import com.rizrmdhn.restaurantapp.ui.theme.RestaurantAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantCard(
    name: String,
    description: String,
    pictureId: String,
    city: String,
    rating: Double,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
        ) {
            SubcomposeAsyncImage(
                model = Helpers.mediumRestaurantImage(pictureId),
                contentDescription = name,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(
                        min = 150.dp,
                        max = 200.dp
                    )
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
            ) {
                val state = painter.state
                if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                    Box(
                        modifier = Modifier
                            .background(
                                shimmerBrush(
                                    targetValue = 1300f,
                                    showShimmer = true
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
                modifier = Modifier.size(8.dp)
            )
            Column {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(
                    modifier = Modifier.size(8.dp)
                )
                Text(
                    text = description,
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(
                    modifier = Modifier.size(8.dp)
                )
                Text(
                    text = city,
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(
                    modifier = Modifier.size(8.dp)
                )
                Row {
                    Text(
                        text = "Rating: ",
                        fontSize = 14.sp
                    )
                    Text(
                        text = rating.toString(),
                        fontWeight = FontWeight.Bold,
                    )
                }
                Spacer(
                    modifier = Modifier.size(8.dp)
                )
                RatingStar(rating)
            }
        }
    }
}

@Composable
fun RatingStar(rating: Double) {
    Row {
        for (i in 1..5) {
            if (i <= rating) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = Color.Yellow
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RestaurantCardPreview() {
    RestaurantAppTheme {
        RestaurantCard(
            name = "Bakmi GM",
            description = "Bakmi GM is a restaurant chain in Indonesia that specializes in Chinese cuisine.",
            pictureId = "1",
            city = "Jakarta",
            rating = 4.5,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RestaurantCardDarkPreview() {
    RestaurantAppTheme {
        RestaurantCard(
            name = "Bakmi GM",
            description = "Bakmi GM is a restaurant chain in Indonesia that specializes in Chinese cuisine.",
            pictureId = "1",
            city = "Jakarta",
            rating = 4.5,
            onClick = {}
        )
    }
}
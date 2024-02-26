package com.example.restaurantapp.ui.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.restaurantapp.common.Helpers
import com.example.restaurantapp.ui.theme.RestaurantAppTheme

@Composable
fun RestaurantCustomerReviews(
    name: String,
    review: String,
    date: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(300.dp)
            .clip(
                RoundedCornerShape(16.dp)
            )
            .background(
                color = MaterialTheme.colorScheme.onBackground
            )
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SubcomposeAsyncImage(
                model = Helpers.avatarGenerator(name),
                contentDescription = name,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .size(32.dp)
                    .clip(
                        CircleShape
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
                            .size(32.dp)
                            .clip(CircleShape)
                    )
                } else {
                    SubcomposeAsyncImageContent()
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.background,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = review,
            color = MaterialTheme.colorScheme.background,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = date,
            color = MaterialTheme.colorScheme.background,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RestaurantCustomerReviewsPreview() {
    RestaurantAppTheme {
        RestaurantCustomerReviews(
            name = "John Doe",
            review = "This is a review",
            date = "2021-08-08"
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RestaurantCustomerReviewsDarkPreview() {
    RestaurantAppTheme {
        RestaurantCustomerReviews(
            name = "John Doe",
            review = "This is a review",
            date = "2021-08-08"
        )
    }
}

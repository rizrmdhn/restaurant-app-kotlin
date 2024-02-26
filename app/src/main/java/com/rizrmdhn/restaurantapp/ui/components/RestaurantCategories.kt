package com.rizrmdhn.restaurantapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rizrmdhn.restaurantapp.ui.theme.RestaurantAppTheme

@Composable
fun RestaurantCategories(
    categories: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .width(IntrinsicSize.Max)
            .height(IntrinsicSize.Max)
            .clip(
                RoundedCornerShape(10.dp)
            )
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(8.dp)
    ) {
        Text(
            text = categories,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.background
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RestaurantCategoriesPreview() {
    RestaurantAppTheme {
        RestaurantCategories(categories = "Seafood")
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RestaurantCategoriesPreviewDark() {
    RestaurantAppTheme {
        RestaurantCategories(categories = "Seafood")
    }
}

package com.example.restaurantapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.restaurantapp.R
import com.example.restaurantapp.ui.theme.RestaurantAppTheme



@Composable
fun RestaurantFoods(
    name: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .width(IntrinsicSize.Max)
            .heightIn(
                min = 100.dp
            )
            .clip(
                RoundedCornerShape(16.dp)
            )
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(16.dp)
    ) {
        Icon(
            painter = painterResource(
                R.drawable.baseline_fastfood_24
            ),
            contentDescription = "Drinks",
            tint = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = name,
            color = MaterialTheme.colorScheme.background,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RestaurantFoodsDefaultPreview() {
    RestaurantAppTheme {
        RestaurantFoods(
            name = "Foods"
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RestaurantFoodsDarkPreview() {
    RestaurantAppTheme {
        RestaurantFoods(
            name = "Foods"
        )
    }
}

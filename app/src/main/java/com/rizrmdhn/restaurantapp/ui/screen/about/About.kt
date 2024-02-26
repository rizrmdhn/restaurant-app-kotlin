package com.rizrmdhn.restaurantapp.ui.screen.about

import android.content.res.Configuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.rizrmdhn.restaurantapp.ui.theme.RestaurantAppTheme

@Composable
fun AboutScreen() {
    AboutContent()
}

@Composable
fun AboutContent() {
    Text(text = "About Screen")
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    RestaurantAppTheme {
        AboutScreen()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AboutScreenDarkPreview() {
    RestaurantAppTheme {
        AboutScreen()
    }
}


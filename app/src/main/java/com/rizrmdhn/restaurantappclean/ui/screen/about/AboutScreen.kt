package com.rizrmdhn.restaurantappclean.ui.screen.about

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.rizrmdhn.core.common.Constants
import com.rizrmdhn.core.ui.theme.RestaurantAppTheme
import com.rizrmdhn.restaurantappclean.R
import com.rizrmdhn.restaurantappclean.ui.components.shimmerBrush

@Composable
fun AboutScreen(
    navigateBack: () -> Unit,
    onToggleDarkMode: () -> Unit,
    isInDarkMode: Boolean,
) {
    AboutContent(
        navigateBack = navigateBack,
        onToggleDarkMode = onToggleDarkMode,
        isInDarkMode = isInDarkMode
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutContent(
    navigateBack: () -> Unit,
    onToggleDarkMode: () -> Unit,
    isInDarkMode: Boolean,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "About",
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
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SubcomposeAsyncImage(
                model = Constants.gitubAvatar,
                contentDescription = Constants.githubUsername,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(200.dp)
                    .heightIn(200.dp)
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
                            .size(64.dp)
                            .clip(CircleShape)
                    )
                } else {
                    SubcomposeAsyncImageContent()
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = Constants.githubUsername,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = Constants.myEmail,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    RestaurantAppTheme {
        AboutContent(
            navigateBack = {},
            onToggleDarkMode = {},
            isInDarkMode = false
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AboutScreenDarkPreview() {
    RestaurantAppTheme {
        AboutContent(
            navigateBack = {},
            onToggleDarkMode = {},
            isInDarkMode = true
        )
    }
}


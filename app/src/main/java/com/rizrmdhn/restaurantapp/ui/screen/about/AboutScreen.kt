package com.rizrmdhn.restaurantapp.ui.screen.about

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.rizrmdhn.restaurantapp.R
import com.rizrmdhn.restaurantapp.common.Constants
import com.rizrmdhn.restaurantapp.common.Helpers
import com.rizrmdhn.restaurantapp.data.Result
import com.rizrmdhn.restaurantapp.data.remote.response.GithubDetailUser
import com.rizrmdhn.restaurantapp.ui.components.ErrorText
import com.rizrmdhn.restaurantapp.ui.components.shimmerBrush
import com.rizrmdhn.restaurantapp.ui.theme.RestaurantAppTheme

@Composable
fun AboutScreen(
    viewModel: AboutScreenViewModel = viewModel(
        factory = Helpers.viewModelFactoryHelper(
            LocalContext.current
        )
    ), navigateBack: () -> Unit, isInDarkMode: Boolean, onToggleDarkMode: () -> Unit
) {
    viewModel.state.collectAsState(initial = Result.Loading).value.let { state ->
        when (state) {
            is Result.Loading -> {
                viewModel.getGithubUserDetail()
            }

            is Result.Success -> {
                AboutContent(
                    user = state.data,
                    navigateBack = navigateBack,
                    onToggleDarkMode = onToggleDarkMode,
                    isInDarkMode = isInDarkMode
                )
            }

            is Result.Error -> {
                ErrorText(
                    error = state.errorMessage
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutContent(
    user: GithubDetailUser,
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
                model = user.avatarUrl,
                contentDescription = user.name,
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
                text = user.name,
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
            user = GithubDetailUser(
                name = "Noor Rizki Ramdhan",
                avatarUrl = "https://avatars.githubusercontent.com/u/10637265?v=4",
                bio = "Android Developer",
                company = "",
                location = "Indonesia",
                url = "",
                hireable = false,
                gravatarId = "",
                gistsUrl = "",
                createdAt = "",
                login = "rizrmdhn",
                id = 1,
                blog = "",
                email = "",
                eventsUrl = "",
                followers = 4,
                followersUrl = "",
                following = 5,
                followingUrl = "",
                htmlUrl = "",
                nodeId = "",
                organizationsUrl = "",
                publicGists = 1,
                publicRepos = 1,
                receivedEventsUrl = "",
                reposUrl = "",
                siteAdmin = false,
                starredUrl = "",
                subscriptionsUrl = "",
                twitterUsername = "",
                type = "",
                updatedAt = "",
            ),
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
            user = GithubDetailUser(
                name = "Noor Rizki Ramdhan",
                avatarUrl = "https://avatars.githubusercontent.com/u/10637265?v=4",
                bio = "Android Developer",
                company = "",
                location = "Indonesia",
                url = "",
                hireable = false,
                gravatarId = "",
                gistsUrl = "",
                createdAt = "",
                login = "rizrmdhn",
                id = 1,
                blog = "",
                email = "",
                eventsUrl = "",
                followers = 4,
                followersUrl = "",
                following = 5,
                followingUrl = "",
                htmlUrl = "",
                nodeId = "",
                organizationsUrl = "",
                publicGists = 1,
                publicRepos = 1,
                receivedEventsUrl = "",
                reposUrl = "",
                siteAdmin = false,
                starredUrl = "",
                subscriptionsUrl = "",
                twitterUsername = "",
                type = "",
                updatedAt = "",
            ),
            navigateBack = {},
            onToggleDarkMode = {},
            isInDarkMode = true
        )
    }
}


package com.rizrmdhn.restaurantappclean

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rizrmdhn.core.di.databaseModule
import com.rizrmdhn.core.di.networkModule
import com.rizrmdhn.core.di.repositoryModule
import com.rizrmdhn.restaurantappclean.di.useCaseModule
import com.rizrmdhn.restaurantappclean.di.viewModelModule
import com.rizrmdhn.restaurantappclean.ui.RestaurantApp
import com.rizrmdhn.restaurantappclean.ui.theme.RestaurantAppCleanTheme
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainActivity)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
        setContent {
            RestaurantAppCleanTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RestaurantApp()
                }
            }
        }
    }
}

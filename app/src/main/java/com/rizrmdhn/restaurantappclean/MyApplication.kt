package com.rizrmdhn.restaurantappclean

import android.app.Application
import com.rizrmdhn.core.di.networkModule
import com.rizrmdhn.core.di.repositoryModule
import com.rizrmdhn.restaurantappclean.di.useCaseModule
import com.rizrmdhn.restaurantappclean.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}
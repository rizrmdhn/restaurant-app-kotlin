package com.rizrmdhn.core.di

import androidx.room.Room
import com.rizrmdhn.core.BuildConfig
import com.rizrmdhn.core.data.RestaurantRepository
import com.rizrmdhn.core.data.source.local.LocalDataSource
import com.rizrmdhn.core.data.source.local.preferences.SettingPreferences
import com.rizrmdhn.core.data.source.local.preferences.dataStore
import com.rizrmdhn.core.data.source.local.room.RestaurantDatabase
import com.rizrmdhn.core.data.source.remote.RemoteDataSource
import com.rizrmdhn.core.data.source.remote.network.ApiService
import com.rizrmdhn.core.domain.repository.IRestaurantRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<RestaurantDatabase>().restaurantDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            RestaurantDatabase::class.java, "FavoriteRestaurant.db"
        ).fallbackToDestructiveMigration().build()
    }
}


val preferencesModule = module {
    single {
        val settingPreferences = SettingPreferences(androidContext().dataStore)
        settingPreferences
    }
}


val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get(), get()) }
    single { RemoteDataSource(get()) }
    single<IRestaurantRepository> {
        RestaurantRepository(
            get(),
            get()
        )
    }
}
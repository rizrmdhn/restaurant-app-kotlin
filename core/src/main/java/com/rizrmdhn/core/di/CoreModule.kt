package com.rizrmdhn.core.di

import androidx.room.Room
import com.rizrmdhn.core.BuildConfig
import com.rizrmdhn.core.common.Constants
import com.rizrmdhn.core.data.RestaurantRepository
import com.rizrmdhn.core.data.source.local.LocalDataSource
import com.rizrmdhn.core.data.source.local.preferences.SettingPreferences
import com.rizrmdhn.core.data.source.local.preferences.dataStore
import com.rizrmdhn.core.data.source.local.room.RestaurantDatabase
import com.rizrmdhn.core.data.source.remote.RemoteDataSource
import com.rizrmdhn.core.data.source.remote.network.ApiService
import com.rizrmdhn.core.domain.repository.IRestaurantRepository
import com.rizrmdhn.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
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
        val passphrase: ByteArray = SQLiteDatabase.getBytes(Constants.appName.toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            RestaurantDatabase::class.java, "FavoriteRestaurant.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
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
        val certificatePinner = CertificatePinner.Builder()
            .add(BuildConfig.BASE_URL_HOSTNAME, "sha256/qRXOtBLL57LL0c7e8w/vou6FL8GMrasDduMdcQqXeBw=")
            .add(BuildConfig.BASE_URL_HOSTNAME, "sha256/jQJTbIh0grw0/1TkHSumWb+Fs0Ggogr621gT3PvPKG0=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                else HttpLoggingInterceptor().setLevel(
                    HttpLoggingInterceptor.Level.NONE
                )
            ).connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
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
    factory { AppExecutors() }
    single<IRestaurantRepository> {
        RestaurantRepository(
            get(),
            get(),
            get()
        )
    }
}
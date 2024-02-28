package com.rizrmdhn.restaurantappclean.di

import com.rizrmdhn.core.domain.usecase.RestaurantInteractor
import com.rizrmdhn.core.domain.usecase.RestaurantUseCase
import com.rizrmdhn.restaurantappclean.ui.screen.favorite.FavoriteScreenViewModel
import com.rizrmdhn.restaurantappclean.ui.screen.home.HomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<RestaurantUseCase> { RestaurantInteractor(get()) }
}

val viewModelModule = module {
     viewModel { HomeScreenViewModel(get()) }
     viewModel { FavoriteScreenViewModel(get()) }
//     viewModel { DetailTourismViewModel(get()) }
}
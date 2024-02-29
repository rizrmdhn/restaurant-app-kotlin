package com.rizrmdhn.restaurantappclean.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizrmdhn.core.domain.usecase.RestaurantUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RestaurantAppCleanViewModel(
    private val restaurantUseCase: RestaurantUseCase
): ViewModel() {
    private val _darkMode = MutableStateFlow(false)
    val darkMode = _darkMode

    fun getDarkMode() {
        viewModelScope.launch {
            restaurantUseCase.getDarkMode().collect {
                _darkMode.value = it
            }
        }
    }

     fun setDarkMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            restaurantUseCase.setDarkMode(isDarkMode)
            _darkMode.value = isDarkMode
        }
    }
}
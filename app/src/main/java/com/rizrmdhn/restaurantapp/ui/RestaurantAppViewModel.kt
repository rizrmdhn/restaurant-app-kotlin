package com.rizrmdhn.restaurantapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizrmdhn.restaurantapp.data.local.dataStore.SettingPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RestaurantAppViewModel(
    private val preferences: SettingPreferences
): ViewModel() {
    private val _darkMode: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val darkMode: StateFlow<Boolean> get() = _darkMode


    fun getDarkMode() {
        viewModelScope.launch {
            preferences.getThemeSetting().collect {
                _darkMode.value = it
            }
        }
    }

    fun setDarkMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            preferences.saveThemeSetting(isDarkMode)
            _darkMode.value = isDarkMode
        }
    }
}
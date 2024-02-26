package com.example.restaurantapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.data.local.dataStore.SettingPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RestaurantAppViewModel(
    private val preferences: SettingPreferences
): ViewModel() {
    private val _darkMode: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val darkMode: MutableStateFlow<Boolean> get() = _darkMode

    private val _isSearchOpen: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isSearchOpen: MutableStateFlow<Boolean> get() = _isSearchOpen

    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchQuery: MutableStateFlow<String> get() = _searchQuery

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

    fun setSearchOpen(isOpen: Boolean) {
        _isSearchOpen.value = isOpen
    }

    fun onSearch(query: String) {
        _searchQuery.value = query
    }

    fun clearQuery() {
        if (_searchQuery.value.isBlank()) {
            _isSearchOpen.value = false
        } else {
            _searchQuery.value = ""
        }
    }
}
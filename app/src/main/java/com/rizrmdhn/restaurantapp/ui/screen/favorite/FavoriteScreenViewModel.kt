package com.rizrmdhn.restaurantapp.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizrmdhn.restaurantapp.data.RestaurantRepository
import com.rizrmdhn.restaurantapp.data.Result
import com.rizrmdhn.restaurantapp.data.local.entity.FavoriteRestaurantEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteScreenViewModel(
    private val repository: RestaurantRepository
) : ViewModel() {
    private val _state: MutableStateFlow<Result<List<FavoriteRestaurantEntity>>> =
        MutableStateFlow(Result.Loading)
    val state: StateFlow<Result<List<FavoriteRestaurantEntity>>> get() = _state

    private val _isSearchOpen: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isSearchOpen: StateFlow<Boolean> get() = _isSearchOpen

    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery


    fun getFavoriteRestaurants() {
        viewModelScope.launch {
            repository.getFavoriteRestaurant().catch {
                _state.value = Result.Error(it.message.toString())
            }.collect {
                _state.value = Result.Success(it)
            }
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
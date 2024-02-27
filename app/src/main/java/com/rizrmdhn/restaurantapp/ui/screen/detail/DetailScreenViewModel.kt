package com.rizrmdhn.restaurantapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizrmdhn.restaurantapp.data.RestaurantRepository
import com.rizrmdhn.restaurantapp.data.Result
import com.rizrmdhn.restaurantapp.data.local.entity.FavoriteRestaurantEntity
import com.rizrmdhn.restaurantapp.data.remote.response.Restaurant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailScreenViewModel(
    private val repository: RestaurantRepository
): ViewModel() {
    private val _state: MutableStateFlow<Result<Restaurant>> = MutableStateFlow(Result.Loading)
    val state: StateFlow<Result<Restaurant>> get() = _state

    private val _isFavoriteRestaurant = MutableStateFlow(false)
    val isFavoriteRestaurant: StateFlow<Boolean> get() = _isFavoriteRestaurant

    fun getDetailRestaurant(id: String) {
        viewModelScope.launch {
            _state.value = Result.Loading
            repository.getDetailRestaurant(id)
                .catch { e ->
                    _state.value = Result.Error(e.message.toString())
                }
                .collect { restaurant ->
                   if (restaurant != null) {
                       _state.value = Result.Success(restaurant)
                   }
                }
        }
    }

    fun insertFavoriteRestaurant(restaurant: FavoriteRestaurantEntity) {
        viewModelScope.launch {
            if (_isFavoriteRestaurant.value) {
                repository.deleteFavoriteRestaurant(restaurant.id)
            } else {
                repository.insertFavoriteRestaurant(restaurant)
            }
        }
    }

    fun isFavoriteRestaurant(id: String) {
        viewModelScope.launch {
            repository.isFavoriteRestaurant(id).collect {
                _isFavoriteRestaurant.value = it
            }
        }
    }
}
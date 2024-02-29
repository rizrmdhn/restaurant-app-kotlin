package com.rizrmdhn.restaurantappclean.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizrmdhn.core.data.Resource
import com.rizrmdhn.core.domain.model.Restaurant
import com.rizrmdhn.core.domain.model.RestaurantDetail
import com.rizrmdhn.core.domain.usecase.RestaurantUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailScreenViewModel(
    private val restaurantUseCase: RestaurantUseCase
): ViewModel() {
    private val _state: MutableStateFlow<Resource<RestaurantDetail>> = MutableStateFlow(Resource.Loading())
    val state: StateFlow<Resource<RestaurantDetail>> get() = _state

    private val _isFavoriteRestaurant = MutableStateFlow(false)
    val isFavoriteRestaurant: StateFlow<Boolean> get() = _isFavoriteRestaurant

    fun getDetailRestaurant(id: String) {
        viewModelScope.launch {
            _state.value = Resource.Loading()
            restaurantUseCase.getDetailRestaurant(id)
                .catch { e ->
                    _state.value = Resource.Error(e.message.toString())
                }
                .collect { restaurant ->
                    _state.value = restaurant
                }
        }
    }

    fun insertFavoriteRestaurant(restaurant: Restaurant,boolean: Boolean) {
        viewModelScope.launch {
            if (_isFavoriteRestaurant.value) {
                restaurantUseCase.setFavoriteRestaurant(restaurant, false)
            } else {
                restaurantUseCase.setFavoriteRestaurant(restaurant, true)
            }
        }
    }

    fun isFavoriteRestaurant(id: String) {
        viewModelScope.launch {
            restaurantUseCase.isFavoriteRestaurant(id).collect {
                _isFavoriteRestaurant.value = it
            }
        }
    }
}
package com.rizrmdhn.core.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizrmdhn.core.data.Resource
import com.rizrmdhn.core.domain.model.Restaurant
import com.rizrmdhn.core.domain.usecase.RestaurantUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteScreenViewModel(
    private val restaurantUseCase: RestaurantUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<Resource<List<Restaurant>>> =
        MutableStateFlow(Resource.Loading())
    val state: StateFlow<Resource<List<Restaurant>>> get() = _state

    fun getFavoriteRestaurants() {
        viewModelScope.launch {
            restaurantUseCase.getFavoriteRestaurant().catch {
                _state.value = Resource.Error(it.message.toString())
            }.collect {
                _state.value = it
            }
        }
    }
}
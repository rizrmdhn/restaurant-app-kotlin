package com.rizrmdhn.restaurantappclean.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rizrmdhn.core.data.Resource
import com.rizrmdhn.core.domain.model.Restaurant
import com.rizrmdhn.core.domain.usecase.RestaurantUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val restaurantUseCase: RestaurantUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<Resource<List<Restaurant>>> =
        MutableStateFlow(Resource.Loading())
    val state: StateFlow<Resource<List<Restaurant>>> get() = _state

    fun getRestaurants() {
        viewModelScope.launch {
            _state.value = Resource.Loading()
            restaurantUseCase.getRestaurant().catch {
                _state.value = Resource.Error(it.message.toString())
            }.collect {
                _state.value = it
            }
        }
    }
}
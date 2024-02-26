package com.example.restaurantapp.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.data.Result
import com.example.restaurantapp.data.remote.response.RestaurantsItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val repository: RestaurantRepository
) : ViewModel() {
    private val _state: MutableStateFlow<Result<List<RestaurantsItem>>> =
        MutableStateFlow(Result.Loading)
    val state: MutableStateFlow<Result<List<RestaurantsItem>>> get() = _state

    fun getRestaurants() {
        viewModelScope.launch {
            repository.getRestaurant().catch {
                _state.value = Result.Error(it.message.toString())
            }.collect {
                _state.value = Result.Success(it)
            }
        }
    }
}
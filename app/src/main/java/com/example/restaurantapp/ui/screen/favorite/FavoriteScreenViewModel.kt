package com.example.restaurantapp.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.data.Result
import com.example.restaurantapp.data.local.entity.FavoriteRestaurantEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteScreenViewModel(
    private val repository: RestaurantRepository
) : ViewModel() {
    private val _state: MutableStateFlow<Result<List<FavoriteRestaurantEntity>>> =
        MutableStateFlow(Result.Loading)
    val state: MutableStateFlow<Result<List<FavoriteRestaurantEntity>>> get() = _state

    fun getFavoriteRestaurants() {
        viewModelScope.launch {
            repository.getFavoriteRestaurant().catch {
                _state.value = Result.Error(it.message.toString())
            }.collect {
                _state.value = Result.Success(it)
            }
        }
    }
}
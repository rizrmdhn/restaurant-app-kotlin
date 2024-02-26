package com.example.restaurantapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.data.Result
import com.example.restaurantapp.data.remote.response.Restaurant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailScreenViewModel(
    private val repository: RestaurantRepository
): ViewModel() {
    private val _state: MutableStateFlow<Result<Restaurant>> = MutableStateFlow(Result.Loading)
    val state: MutableStateFlow<Result<Restaurant>> get() = _state

    fun getDetailRestaurant(id: String) {
        viewModelScope.launch {
            repository.getDetailRestaurant(id).catch {
                _state.value = Result.Error(it.message.toString())
            }.collect {
                if (it != null) {
                    _state.value = Result.Success(it)
                }
            }
        }
    }
}
package com.rizrmdhn.restaurantappclean.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizrmdhn.core.data.Resource
import com.rizrmdhn.core.domain.model.Restaurant
import com.rizrmdhn.core.domain.usecase.RestaurantUseCase
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

    private val _isSearchOpen = MutableStateFlow(false)
    val isSearchOpen: StateFlow<Boolean> get() = _isSearchOpen

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

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

    fun setSearchOpen(isOpen: Boolean) {
        _isSearchOpen.value = isOpen
    }

    fun onQueryChange(query: String) {
        _query.value = query
    }

    fun onSearch() {
        if (_query.value.isNotBlank()) {
            viewModelScope.launch {
                _state.value = Resource.Loading()
                restaurantUseCase.searchRestaurant(_query.value).catch {
                    _state.value = Resource.Error(it.message.toString())
                }.collect {
                    _state.value = it
                }
            }
        } else {
            getRestaurants()
        }
    }

    fun clearQuery() {
        if (_query.value.isBlank()) {
            getRestaurants()
            setSearchOpen(false)
            _query.value = ""
        } else {
            _query.value = ""
        }
    }
}
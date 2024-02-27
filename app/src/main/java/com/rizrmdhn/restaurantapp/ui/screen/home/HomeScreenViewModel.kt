package com.rizrmdhn.restaurantapp.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizrmdhn.restaurantapp.data.RestaurantRepository
import com.rizrmdhn.restaurantapp.data.Result
import com.rizrmdhn.restaurantapp.data.remote.response.RestaurantsItem
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val repository: RestaurantRepository
) : ViewModel() {
    private val _state: MutableStateFlow<Result<List<RestaurantsItem>>> =
        MutableStateFlow(Result.Loading)
    val state: StateFlow<Result<List<RestaurantsItem>>> get() = _state

    private val _isSearchOpen: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isSearchOpen: StateFlow<Boolean> get() = _isSearchOpen

    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    private var currentSearchJob: Job? = null

    fun getRestaurants() {
        viewModelScope.launch {
            _state.value = Result.Loading
            repository.getRestaurant().catch {
                _state.value = Result.Error(it.message.toString())
            }.collect {
                _state.value = Result.Success(it)
            }
        }
    }

    @OptIn(FlowPreview::class)
    fun searchRestaurants(query: String) {
        viewModelScope.launch {
            _state.value = Result.Loading
            if (query.isBlank()) {
                _searchQuery.value = query
                getRestaurants()
            } else {
                _searchQuery.value = query
                // Cancel the previous search operation if it's still active
                cancelPreviousSearch()

                // Start a new search operation
                val searchJob = launch {
                    _searchQuery.debounce(300)
                        .collectLatest { searchQuery ->
                            repository.searchRestaurant(searchQuery)
                                .catch { e ->
                                    _state.value = Result.Error(e.message ?: "An error occurred")
                                }
                                .collect { restaurants ->
                                    _state.value = Result.Success(restaurants)
                                }
                        }
                }
                // Store the reference to the current search job
                currentSearchJob = searchJob
            }
        }
    }

    fun setSearchOpen(isOpen: Boolean) {
        _isSearchOpen.value = isOpen
    }


    fun clearQuery() {
        if (_searchQuery.value.isBlank()) {
            _isSearchOpen.value = false
        } else {
            _searchQuery.value = ""
            getRestaurants()
        }
    }

    private fun cancelPreviousSearch() {
        currentSearchJob?.cancel()
        currentSearchJob = null
    }
}
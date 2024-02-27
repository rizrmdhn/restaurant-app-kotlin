package com.rizrmdhn.restaurantapp.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizrmdhn.restaurantapp.data.RestaurantRepository
import com.rizrmdhn.restaurantapp.data.Result
import com.rizrmdhn.restaurantapp.data.local.entity.FavoriteRestaurantEntity
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
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


    private var currentSearchJob: Job? = null

    fun getFavoriteRestaurants() {
        viewModelScope.launch {
            _state.value = Result.Loading
            repository.getFavoriteRestaurant().catch {
                _state.value = Result.Error(it.message.toString())
            }.collect {
                _state.value = Result.Success(it)
            }
        }
    }

    @OptIn(FlowPreview::class)
    fun searchFavoriteRestaurants(newQuery: String) {
        viewModelScope.launch {
            _state.value = Result.Loading
            if (newQuery.isBlank()) {
                _searchQuery.value = newQuery
                getFavoriteRestaurants()
            } else {
                _searchQuery.value = newQuery
                // Cancel the previous search operation if it's still active
                cancelPreviousSearch()

                // Start a new search operation
                val searchJob = launch {
                    _searchQuery.debounce(300)
                        .collectLatest { searchQuery ->
                            repository.searchFavoriteRestaurant(searchQuery)
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
            getFavoriteRestaurants()
        } else {
            _searchQuery.value = ""
            getFavoriteRestaurants()
        }
    }

    private fun cancelPreviousSearch() {
        currentSearchJob?.cancel()
        currentSearchJob = null
    }
}
package com.rizrmdhn.restaurantappclean.ui.screen.home

import androidx.lifecycle.ViewModel
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
    restaurantUseCase: RestaurantUseCase
) : ViewModel() {
   val restaurant = restaurantUseCase.getRestaurant()

//    @OptIn(FlowPreview::class)
//    fun searchRestaurants(query: String) {
//        viewModelScope.launch {
//            _state.value = Result.Loading
//            if (query.isBlank()) {
//                _searchQuery.value = query
//                getRestaurants()
//            } else {
//                _searchQuery.value = query
//                // Cancel the previous search operation if it's still active
//                cancelPreviousSearch()
//
//                // Start a new search operation
//                val searchJob = launch {
//                    _searchQuery.debounce(300)
//                        .collectLatest { searchQuery ->
//                            repository.searchRestaurant(searchQuery)
//                                .catch { e ->
//                                    _state.value = Result.Error(e.message ?: "An error occurred")
//                                }
//                                .collect { restaurants ->
//                                    _state.value = Result.Success(restaurants)
//                                }
//                        }
//                }
//                // Store the reference to the current search job
//                currentSearchJob = searchJob
//            }
//        }
//    }

}
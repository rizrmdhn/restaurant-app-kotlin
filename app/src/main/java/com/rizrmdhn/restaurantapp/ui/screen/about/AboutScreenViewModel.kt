package com.rizrmdhn.restaurantapp.ui.screen.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizrmdhn.restaurantapp.data.GithubRepository
import com.rizrmdhn.restaurantapp.data.Result
import com.rizrmdhn.restaurantapp.data.remote.response.GithubDetailUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AboutScreenViewModel(
    private val repository: GithubRepository
) : ViewModel() {
    private val _state: MutableStateFlow<Result<GithubDetailUser>> =
        MutableStateFlow(Result.Loading)
    val state: StateFlow<Result<GithubDetailUser>> get() = _state


    fun getGithubUserDetail() {
        viewModelScope.launch {
            _state.value = Result.Loading
            repository.getDetailGithubUser()
                .catch { e ->
                    _state.value = Result.Error(e.message.toString())
                }
                .collect { user ->
                    _state.value = Result.Success(user)
                }
        }
    }
}
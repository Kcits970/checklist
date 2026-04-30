package com.example.dailybloom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository = UserRepository()): ViewModel() {

    private val _user = MutableStateFlow(emptyUser)
    val user: StateFlow<User> = _user.asStateFlow()

    suspend fun loadUser(email: String) {
        val fetchedUser = repository.queryUser(email)
        if (fetchedUser != null)
            _user.value = fetchedUser
    }

    fun clearUser() {
        _user.value = emptyUser
    }
}
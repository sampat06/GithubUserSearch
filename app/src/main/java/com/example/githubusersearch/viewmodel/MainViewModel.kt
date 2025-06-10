package com.example.githubusersearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusersearch.data.model.GitHubUser
import com.example.githubusersearch.data.model.Repository
import com.example.githubusersearch.data.repository.RetrofitInstance
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val user = MutableLiveData<GitHubUser?>()
    val repository = MutableLiveData<List<Repository>>()
    val error = MutableLiveData<String>()
    val showingLoader = MutableLiveData<Boolean>()

    fun fetchUser(username: String) = viewModelScope.launch {
        showingLoader.value = false
        try {
            val response = RetrofitInstance.api.getUser(username)
            if (response.isSuccessful) {
                showingLoader.value = true
                user.value = response.body()
                fetchRepository(username)
            } else {
                error.value = "User not found"
            }
        } catch (e: Exception) {
            showingLoader.value = false
            error.value = "Network error"
        }
    }

    private fun fetchRepository(username: String) = viewModelScope.launch {
        val response = RetrofitInstance.api.getRepos(username)
        if (response.isSuccessful) {
            repository.value = response.body()
        }
    }
}

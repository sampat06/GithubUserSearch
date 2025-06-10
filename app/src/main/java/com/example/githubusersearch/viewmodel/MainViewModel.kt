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
    val repos = MutableLiveData<List<Repository>>()
    val error = MutableLiveData<String>()

    fun fetchUser(username: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getUser(username)
                if (response.isSuccessful) {
                    println("qwerty fetchUser ${response.body()}")
                    user.value = response.body()
                    fetchRepository(username)
                } else {
                    error.value = "User not found"
                }
            } catch (e: Exception) {
                error.value = "Network error"
            }
        }
    }

    private fun fetchRepository(username: String) {
        viewModelScope.launch {
            val response = RetrofitInstance.api.getRepos(username)
            if (response.isSuccessful) {
                println("qwerty fetchRepos ${response.body()}")
                repos.value = response.body()
            }
        }
    }
}

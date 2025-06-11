package com.example.githubusersearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusersearch.data.model.GitHubUser
import com.example.githubusersearch.data.model.Repository
import com.example.githubusersearch.data.repository.RetrofitInstance
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val userDetails = MutableLiveData<GitHubUser?>()
    val repositoryDetails = MutableLiveData<List<Repository>>()
    val error = MutableLiveData<String>()
    val showingLoader = MutableLiveData<Boolean>()

    fun fetchUser(username: String) = viewModelScope.launch {
        showingLoader.postValue(true)
        error.postValue("")
        try {
            val response = RetrofitInstance.api.getUser(username)
            if (response.isSuccessful) {
                userDetails.postValue(response.body())
                fetchRepository(username)
            } else {
                error.postValue("User not found")
            }
            showingLoader.postValue(false)
        } catch (e: Exception) {
            error.postValue("Network error")
            showingLoader.postValue(false)
        }
    }

    private fun fetchRepository(username: String) = viewModelScope.launch {
        val response = RetrofitInstance.api.getRepos(username)
        if (response.isSuccessful) {
            repositoryDetails.postValue(response.body())
        }
    }
}

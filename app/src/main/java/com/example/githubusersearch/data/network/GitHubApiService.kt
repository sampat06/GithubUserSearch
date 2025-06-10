package com.example.githubusersearch.data.network

import com.example.githubusersearch.data.model.GitHubUser
import com.example.githubusersearch.data.model.Repository
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApiService {
    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): Response<GitHubUser>

    @GET("users/{username}/repos")
    suspend fun getRepos(@Path("username") username: String): Response<List<Repository>>
}
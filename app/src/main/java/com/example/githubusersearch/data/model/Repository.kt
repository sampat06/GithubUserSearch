package com.example.githubusersearch.data.model

data class Repository(
    val name: String,
    val description: String?,
    val stargazers_count: Int,
    val forks_count: Int
)

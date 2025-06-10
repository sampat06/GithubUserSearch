package com.example.githubusersearch.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubusersearch.data.model.GitHubUser
import com.example.githubusersearch.databinding.ItemUserBinding

class UserAdapter(private val users: List<GitHubUser>, private val onClick: (GitHubUser) -> Unit) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.binding.username.text = user.login
        Glide.with(holder.itemView.context).load(user.avatar_url).into(holder.binding.avatar)
        holder.itemView.setOnClickListener { onClick(user) }
    }

    override fun getItemCount() = users.size
}
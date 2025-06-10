package com.example.githubusersearch.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusersearch.data.model.Repository
import com.example.githubusersearch.databinding.ItemRepositoryBinding

class RepoAdapter : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    private val repos = mutableListOf<Repository>()

    fun submitList(list: List<Repository>) {
        repos.clear()
        repos.addAll(list)
        notifyDataSetChanged()
    }

    class RepoViewHolder(val binding: ItemRepositoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = repos[position]
        holder.binding.repoName.text = repo.name
        holder.binding.repoDescription.text = repo.description ?: "No description"
        holder.binding.stars.text = "★ ${repo.stargazers_count}"
        holder.binding.forks.text = "🍴 ${repo.forks_count}"
    }

    override fun getItemCount(): Int = repos.size
}
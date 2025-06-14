package com.example.githubusersearch.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.githubusersearch.databinding.FragmentProfileBinding
import com.example.githubusersearch.viewmodel.MainViewModel

class ProfileFragment : Fragment() {
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentProfileBinding.inflate(inflater, container, false).apply {
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        imBack.setOnClickListener {
            handleBackPress()
        }

        viewModel.userDetails.observe(viewLifecycleOwner) { user ->
            username.text = user?.login
            followers.text = "${user?.followers} Followers"
            bio.text = user?.bio ?: "No Bio"
            repoCount.text = "${user?.public_repos} Repositories"
            Glide.with(this@ProfileFragment).load(user?.avatar_url).circleCrop().into(avatar)
            user?.public_repos?.takeIf { it != 0 }?.let {
                repositoriesText.visibility = View.VISIBLE
            }
        }

        val adapter = RepoAdapter()
        repoRecyclerView.adapter = adapter

        viewModel.repositoryDetails.observe(viewLifecycleOwner) { repos ->
            adapter.submitList(repos)
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    handleBackPress()
                }
            }
        )

    }.root

    private fun handleBackPress() {
        viewModel.userDetails.postValue(null)
        val navController = findNavController()
        val didPop = navController.popBackStack()
        if (!didPop) {
            requireActivity().finish()
        }
    }
}



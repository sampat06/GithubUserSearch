package com.example.githubusersearch.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.githubusersearch.R
import com.example.githubusersearch.databinding.FragmentSearchBinding
import com.example.githubusersearch.viewmodel.MainViewModel

class SearchFragment : Fragment() {
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSearchBinding.inflate(inflater, container, false).apply {
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        searchButton.setOnClickListener {
            val username = usernameInput.text.toString()
            viewModel.fetchUser(username)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            errorText.text = it
        }

        viewModel.user.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(R.id.profileFragment)
            }
        }
    }.root
}

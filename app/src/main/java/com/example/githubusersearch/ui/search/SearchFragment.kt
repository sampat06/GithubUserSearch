package com.example.githubusersearch.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.githubusersearch.R
import com.example.githubusersearch.databinding.FragmentSearchBinding
import com.example.githubusersearch.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class SearchFragment : Fragment() {
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSearchBinding.inflate(inflater, container, false).apply {
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        viewModel.showingLoader.observe(viewLifecycleOwner) {
            UIHandleWhenLoaderShows(isLoaderShown = it, binding = this)
        }

        searchLayout.setOnClickListener {
            val username = usernameInput.text.toString()
            username.takeIf { it.isNotEmpty() }?.let {
                viewModel.fetchUser(it)
            } ?: showSnackBar(
                root, resources.getString(R.string.empty_search_error_text), resources.getString(R.string.ok)
            )
        }

        viewModel.userDetails.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(R.id.profileFragment)
                usernameInput.text?.clear()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            errorText.text = it
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        )

    }.root

    private fun UIHandleWhenLoaderShows(isLoaderShown: Boolean, binding: FragmentSearchBinding) {
        binding.apply {
            if (isLoaderShown) {
                searchLayout.isClickable = false
                loader.visibility = View.VISIBLE
                searchText.text = resources.getString(R.string.loading)
                searchLayout.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.gray))
            } else {
                searchLayout.isClickable = true
                loader.visibility = View.GONE
                searchText.text = resources.getString(R.string.search)
                searchLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.purple_200
                    )
                )
            }
        }
    }
}

private fun showSnackBar(view: View, message: String, okText: String) {
    val snackbar = Snackbar
        .make(view, message, Snackbar.LENGTH_LONG)
        .setAction(okText) { }
    snackbar.setActionTextColor(ContextCompat.getColor(view.context, R.color.black))
    val snackBarView = snackbar.view
    snackBarView.setBackgroundColor(ContextCompat.getColor(view.context, R.color.red))
    snackbar.show()
}

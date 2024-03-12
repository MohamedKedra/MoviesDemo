package com.example.moviesdemo.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.moviesdemo.app.base.DataState
import com.example.moviesdemo.data.remote.Result
import com.example.moviesdemo.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    lateinit var binding: FragmentListBinding
    lateinit var adapter: MovieAdapter

    private val viewModel by lazy {
        viewModelProvider[ListViewModel::class.java]
    }

    private val viewModelProvider by lazy {
        ViewModelProvider(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.refreshMovieList()

        adapter = MovieAdapter(requireContext()) { movie ->

        }

        initObservable()
    }

    private fun initObservable() {

        with(binding) {
            viewModel.popularMoviesListResponse.observe(viewLifecycleOwner) { response ->
                when (response.getStatus()) {

                    DataState.DataStatus.LOADING -> {
                        shimmerLoading.startShimmer()
                        shimmerLoading.isVisible = true
                        rvMovies.isVisible = false
                        layoutEmptyOrError.isVisible = false
                    }

                    DataState.DataStatus.SUCCESS -> {

                        shimmerLoading.stopShimmer()
                        shimmerLoading.isVisible = false
                        rvMovies.isVisible = true
                        layoutEmptyOrError.isVisible = false

                        adapter.submitList(response.getData()?.results)
                        rvMovies.adapter = adapter
                    }

                    DataState.DataStatus.ERROR -> {

                        shimmerLoading.stopShimmer()
                        shimmerLoading.isVisible = false
                        rvMovies.isVisible = false
                        layoutEmptyOrError.isVisible = true
                        tvMessage.text = response.getError()?.message
                    }

                    DataState.DataStatus.NO_INTERNET -> {

                        shimmerLoading.stopShimmer()
                        shimmerLoading.isVisible = false
                        rvMovies.isVisible = false
                        layoutEmptyOrError.isVisible = true
                        tvMessage.text = response.getError()?.message
                    }
                }
            }
        }
    }
}
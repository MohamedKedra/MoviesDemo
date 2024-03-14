package com.example.moviesdemo.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.moviesdemo.app.base.DataState
import com.example.moviesdemo.data.workmanager.MovieWorker
import com.example.moviesdemo.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class ListFragment : Fragment() {

    lateinit var binding: FragmentListBinding
    private lateinit var adapter: MovieAdapter
    private lateinit var periodicWorkRequest: PeriodicWorkRequest
    private lateinit var oneTimeWorkRequest: OneTimeWorkRequest

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

        initView()
        initObservable()
    }

    private fun initView() {

        showAppState()

        adapter = MovieAdapter(requireContext()) { movie ->
            val action  = ListFragmentDirections.actionListFragmentToDetailsFragment(movie)
            findNavController().navigate(action)
        }

        periodicWorkRequest =
            PeriodicWorkRequest.Builder(MovieWorker::class.java, 15, TimeUnit.MINUTES)
                .addTag("WorkManager ${System.currentTimeMillis()}")
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()

        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            "Periodic",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )



        val results = viewModel.getCachedMovies()
        println("output ${results.size}")

        if (viewModel.getCachedMovies().isNotEmpty()) {
            println("output db at ${System.currentTimeMillis()}")

            showAppState(isLoading = false)
            adapter.submitList(results)
            binding.rvMovies.adapter = adapter
        } else {
            viewModel.refreshMovieList()
        }
    }


    private fun initObservable() {

        with(binding) {
            viewModel.popularMoviesListResponse.observe(viewLifecycleOwner) { response ->
                when (response.getStatus()) {

                    DataState.DataStatus.LOADING -> {
                        showAppState()
                    }

                    DataState.DataStatus.SUCCESS -> {
                        showAppState(isLoading = false)
                        val list = response.getData()?.results
                        if (list != null) {
                            viewModel.cacheMoviesList(list)
                            adapter.submitList(response.getData()?.results)
                            rvMovies.adapter = adapter
                        } else {
                            showAppState(
                                isLoading = false,
                                isError = true,
                                errorMsg = "Empty List"
                            )
                        }
                    }

                    DataState.DataStatus.ERROR -> {
                        showAppState(
                            isLoading = false,
                            isError = true,
                            errorMsg = response.getError()?.message ?: "Something went wrong"
                        )
                    }

                    DataState.DataStatus.NO_INTERNET -> {
                        showAppState(
                            isLoading = false,
                            isError = true,
                            errorMsg = "No Internet Connection"
                        )
                    }
                }
            }
        }
    }

    private fun showAppState(
        isLoading: Boolean = true,
        isError: Boolean = false,
        errorMsg: String = ""
    ) {
        binding.apply {
            shimmerLoading.apply {
                if (isLoading) startShimmer() else stopShimmer()
                isVisible = isLoading
            }

            rvMovies.isVisible = !(isLoading || isError)
            layoutEmptyOrError.isVisible = isError
            tvMessage.text = errorMsg
        }
    }
}
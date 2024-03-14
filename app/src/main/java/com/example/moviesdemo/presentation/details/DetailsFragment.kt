package com.example.moviesdemo.presentation.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.moviesdemo.R
import com.example.moviesdemo.app.utils.Constant
import com.example.moviesdemo.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    lateinit var fragmentDetailsBinding: FragmentDetailsBinding
    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentDetailsBinding = FragmentDetailsBinding.inflate(inflater)
        return fragmentDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(fragmentDetailsBinding){


            args.result?.apply {
                Glide.with(requireContext()).load(Constant.imageBase.plus(poster_path)).placeholder(
                    R.drawable.ic_movie
                ).into(ivPoster)

                Glide.with(requireContext()).load(Constant.imageBase.plus(backdrop_path)).placeholder(
                    R.drawable.ic_movie
                ).into(ivBackground)

                tvTitle.text = original_title
                tvLanguage.text = original_language
                tvDate.text = release_date
                tvOverview.text = overview
                tvVote.text = vote_average.toString()


            }

        }
    }


}
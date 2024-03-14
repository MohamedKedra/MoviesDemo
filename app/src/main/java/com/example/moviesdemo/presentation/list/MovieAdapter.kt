package com.example.moviesdemo.presentation.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesdemo.R
import com.example.moviesdemo.app.utils.Constant
import com.example.moviesdemo.data.remote.Result
import com.example.moviesdemo.databinding.ItemMovieBinding


class MovieAdapter(private val context: Context, val onItemClick: (Result) -> Unit) :
    ListAdapter<Result, MovieAdapter.MovieHolder>(ProductDiffCallback()) {

    inner class MovieHolder(private val itemProductLayoutBinding: ItemMovieBinding) :
        RecyclerView.ViewHolder(itemProductLayoutBinding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(result: Result) {
            with(itemProductLayoutBinding) {
                result.apply {
                    Glide.with(context).load(Constant.imageBase.plus(poster_path)).placeholder(
                        R.drawable.ic_movie)
                        .into(ivPoster)
                }
            }
        }

        override fun onClick(p0: View?) {
            onItemClick.invoke(getItem(adapterPosition))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


class ProductDiffCallback : DiffUtil.ItemCallback<Result>() {
    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}
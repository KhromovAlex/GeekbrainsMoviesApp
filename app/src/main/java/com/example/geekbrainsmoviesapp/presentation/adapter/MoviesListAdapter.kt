package com.example.geekbrainsmoviesapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.geekbrainsmoviesapp.R
import com.example.geekbrainsmoviesapp.common.Common.BASE_URL_IMAGE
import com.example.geekbrainsmoviesapp.model.Movie

class MoviesListAdapter(val onTap: ((movie: Movie) -> Unit)?) :
    RecyclerView.Adapter<MoviesListAdapter.MoviesListViewHolder>() {
    private val list = mutableListOf<Movie>()

    fun setData(toAdd: List<Movie>) {
        val callback = MoviesListDiffUtilCallback(list, toAdd)
        DiffUtil.calculateDiff(callback).run {
            list.clear()
            list.addAll(toAdd)
            dispatchUpdatesTo(this@MoviesListAdapter)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movies_list_item, parent, false)

        return MoviesListViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    inner class MoviesListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            with(itemView) {
                setOnClickListener {
                    onTap?.invoke(list[absoluteAdapterPosition])
                }
                findViewById<TextView>(R.id.title).text = movie.title
                findViewById<TextView>(R.id.overview).text = movie.overview

                findViewById<ImageView>(R.id.image_poster).run {
                    if (movie.posterPath == null || movie.posterPath!!.trim() == "") {
                        setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_baseline_close_24
                            )
                        )
                        setBackgroundResource(R.color.gray)
                    } else {
                        Glide.with(this)
                            .load("$BASE_URL_IMAGE${movie.posterPath}")
                            .centerCrop()
                            .into(this)
                    }
                }
            }
        }
    }

    inner class MoviesListDiffUtilCallback(
        private val list: List<Movie>,
        private val toAdd: List<Movie>
    ) :
        DiffUtil.Callback() {
        override fun getOldListSize() = list.size

        override fun getNewListSize() = toAdd.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            list[oldItemPosition].id == toAdd[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            list[oldItemPosition] == toAdd[newItemPosition]

    }
}

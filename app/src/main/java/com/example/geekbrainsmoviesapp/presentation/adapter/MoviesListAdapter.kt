package com.example.geekbrainsmoviesapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.geekbrainsmoviesapp.R
import com.example.geekbrainsmoviesapp.model.Movie

class MoviesListAdapter(var onTap: OnTapMovie?) :
    RecyclerView.Adapter<MoviesListAdapter.MoviesListViewHolder>() {
    private val list = mutableListOf<Movie>()

    fun getData(): List<Movie> = list

    fun setData(toAdd: List<Movie>) {
        val callback = MoviesListDiffUtilCallback(list, toAdd)
        val result = DiffUtil.calculateDiff(callback)
        list.clear()
        list.addAll(toAdd)
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movies_list_item, parent, false)

        return MoviesListViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnTapMovie {
        fun openMovieCard(movie: Movie)
    }

    inner class MoviesListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imageView: ImageView

        init {
            itemView.setOnClickListener {
                onTap?.openMovieCard(list[adapterPosition])
            }
            imageView = itemView.findViewById(R.id.image_poster)
        }

        fun bind(movie: Movie) {
            itemView.findViewById<TextView>(R.id.title).text = movie.title
            itemView.findViewById<TextView>(R.id.overview).text = movie.overview

            if (movie.posterPath == "") {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.ic_baseline_close_24
                    )
                )
                imageView.setBackgroundResource(R.color.gray)
            }
        }
    }

    inner class MoviesListDiffUtilCallback(
        private val list: List<Movie>,
        private val toAdd: List<Movie>
    ) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int = list.size

        override fun getNewListSize(): Int = toAdd.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return list[oldItemPosition].id == toAdd[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return list[oldItemPosition] == toAdd[newItemPosition]
        }

    }
}

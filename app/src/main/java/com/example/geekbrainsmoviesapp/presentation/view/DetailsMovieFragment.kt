package com.example.geekbrainsmoviesapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.geekbrainsmoviesapp.R
import com.example.geekbrainsmoviesapp.common.Common
import com.example.geekbrainsmoviesapp.databinding.FragmentDetailsMovieBinding
import com.example.geekbrainsmoviesapp.model.AppState
import com.example.geekbrainsmoviesapp.model.MovieDetails
import com.example.geekbrainsmoviesapp.presentation.viewmodel.MoviesListViewModel
import com.example.geekbrainsmoviesapp.utils.hide
import com.example.geekbrainsmoviesapp.utils.show
import com.example.geekbrainsmoviesapp.utils.showSnack
import java.text.SimpleDateFormat
import java.util.*

class DetailsMovieFragment : Fragment() {
    private val viewModel: MoviesListViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MoviesListViewModel::class.java)
    }
    private var _binding: FragmentDetailsMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieButtonToFavorites.setOnClickListener {
            if (viewModel.getLiveDataCurrentMovie().value is AppState.Success<MovieDetails>) {
                viewModel.addInFavorites((viewModel.getLiveDataCurrentMovie().value as AppState.Success<MovieDetails>).data)

                if (viewModel.isFavoritesMovie((viewModel.getLiveDataCurrentMovie().value as AppState.Success<MovieDetails>).data)) {
                    binding.movieButtonToFavorites.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                } else {
                    binding.movieButtonToFavorites.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                }
            }
        }

        viewModel.setLiveDataCurrentMovie(AppState.Loading())

        viewModel.getLiveDataCurrentIdMovie().observe(viewLifecycleOwner) {
            viewModel.getMovieDetails(it)
        }

        viewModel.getLiveDataCurrentMovie().observe(viewLifecycleOwner) {
            with(binding) {
                when (it) {
                    is AppState.Error<MovieDetails> -> {
                        errorState.show()
                        loadState.hide()
                        successState.hide()
                        errorState.showSnack(R.string.error)
                    }
                    is AppState.Loading<MovieDetails> -> {
                        errorState.hide()
                        loadState.show()
                        successState.hide()
                    }
                    is AppState.Success<MovieDetails> -> {
                        if (viewModel.isFavoritesMovie(it.data)) {
                            movieButtonToFavorites.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                        } else {
                            movieButtonToFavorites.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                        }

                        movieTitle.text = it.data.title
                        movieTitleOriginal.text = it.data.originalTitle
                        movieDescription.text = it.data.overview
                        movieGenres.text =
                            it.data.genres.foldIndexed("") { index, acc, genre -> if (index == it.data.genres.size - 1) "$acc${genre.name}" else "$acc${genre.name}, " }
                        movieRuntime.text =
                            getString(R.string.placeholder_time_min, it.data.runtime)
                        movieVoteAverage.text = it.data.voteAverage.toString()
                        movieVoteCount.text =
                            getString(R.string.placeholder_vote_count, it.data.voteCount)
                        movieBudget.text = getString(R.string.placeholder_budget, it.data.budget)
                        movieRevenue.text = getString(R.string.placeholder_revenue, it.data.revenue)
                        movieReleaseDate.text =
                            getString(
                                R.string.placeholder_release_date,
                                SimpleDateFormat("dd.MM.yyyy", Locale("en")).format(it.data.releaseDate)
                            )

                        if (it.data.posterPath == null || it.data.posterPath!!.trim() == "") {
                            movieImagePoster.setImageDrawable(
                                ContextCompat.getDrawable(
                                    requireContext(),
                                    R.drawable.ic_baseline_close_24
                                )
                            )
                            movieImagePoster.setBackgroundResource(R.color.gray)
                        } else {
                            Glide.with(movieImagePoster)
                                .load("${Common.BASE_URL_IMAGE}${it.data.posterPath}")
                                .centerCrop()
                                .into(movieImagePoster)
                        }

                        errorState.hide()
                        loadState.hide()
                        successState.show()
                    }
                }

            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.example.geekbrainsmoviesapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.geekbrainsmoviesapp.R
import com.example.geekbrainsmoviesapp.databinding.FragmentDetailsMovieBinding
import com.example.geekbrainsmoviesapp.presentation.viewmodel.MoviesListViewModel

class DetailsMovieFragment : Fragment() {
    private lateinit var viewModel: MoviesListViewModel
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

        viewModel = ViewModelProvider(requireActivity()).get(MoviesListViewModel::class.java)

        viewModel.getLiveDataCurrentMovie().observe(viewLifecycleOwner) {
            binding.movieTitle.text = it.title
            binding.movieTitleOriginal.text = it.originalTitle
            binding.movieDescription.text = it.overview
            binding.movieGenres.text =
                it.genres.foldIndexed("") { index, acc, genre -> if (index == it.genres.size - 1) "$acc${genre.name}" else "$acc${genre.name}, " }
            binding.movieRuntime.text = getString(R.string.placeholder_time_min, it.runtime)
            binding.movieVoteAverage.text = it.voteAverage.toString()
            binding.movieVoteCount.text = getString(R.string.placeholder_vote_count, it.voteCount)
            binding.movieBudget.text = getString(R.string.placeholder_budget, it.budget)
            binding.movieRevenue.text = getString(R.string.placeholder_revenue, it.revenue)
            binding.movieReleaseDate.text =
                getString(R.string.placeholder_release_date, it.releaseDate.toString())

            if (it.posterPath == "") {
                binding.movieImagePoster.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_baseline_close_24
                    )
                )
                binding.movieImagePoster.setBackgroundResource(R.color.gray)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

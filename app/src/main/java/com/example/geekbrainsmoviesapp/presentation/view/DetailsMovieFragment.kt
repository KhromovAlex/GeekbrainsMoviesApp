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

        viewModel.getLiveDataCurrentMovie().observe(viewLifecycleOwner) {
            with(binding) {
                movieTitle.text = it.title
                movieTitleOriginal.text = it.originalTitle
                movieDescription.text = it.overview
                movieGenres.text =
                    it.genres.foldIndexed("") { index, acc, genre -> if (index == it.genres.size - 1) "$acc${genre.name}" else "$acc${genre.name}, " }
                movieRuntime.text = getString(R.string.placeholder_time_min, it.runtime)
                movieVoteAverage.text = it.voteAverage.toString()
                movieVoteCount.text = getString(R.string.placeholder_vote_count, it.voteCount)
                movieBudget.text = getString(R.string.placeholder_budget, it.budget)
                movieRevenue.text = getString(R.string.placeholder_revenue, it.revenue)
                movieReleaseDate.text =
                    getString(R.string.placeholder_release_date, it.releaseDate.toString())

                if (it.posterPath == "") {
                    movieImagePoster.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_baseline_close_24
                        )
                    )
                    movieImagePoster.setBackgroundResource(R.color.gray)
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

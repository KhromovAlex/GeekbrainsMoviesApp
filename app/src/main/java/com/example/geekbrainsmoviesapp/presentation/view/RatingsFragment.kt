package com.example.geekbrainsmoviesapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.geekbrainsmoviesapp.databinding.FragmentRatingsBinding
import com.example.geekbrainsmoviesapp.model.AppState
import com.example.geekbrainsmoviesapp.model.Movie
import com.example.geekbrainsmoviesapp.model.MoviesFilter
import com.example.geekbrainsmoviesapp.presentation.adapter.MoviesListAdapter
import com.example.geekbrainsmoviesapp.presentation.viewmodel.MoviesListViewModel

class RatingsFragment : Fragment(), MoviesListAdapter.OnTapMovie {
    private lateinit var viewModel: MoviesListViewModel
    private var _binding: FragmentRatingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRatingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MoviesListViewModel::class.java)
        val adapter = MoviesListAdapter(this)
        binding.listContainer.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.listContainer.adapter = adapter

        if (savedInstanceState == null) {
            viewModel.getMovies(MoviesFilter.Rating)
        }

        viewModel.getLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is AppState.Error<List<Movie>> -> {
                    binding.errorState.visibility = View.VISIBLE
                    binding.loadState.visibility = View.GONE
                    binding.successState.visibility = View.GONE
                }
                is AppState.Loading<List<Movie>> -> {
                    binding.errorState.visibility = View.GONE
                    binding.loadState.visibility = View.VISIBLE
                    binding.successState.visibility = View.GONE
                }
                is AppState.Success<List<Movie>> -> {
                    binding.errorState.visibility = View.GONE
                    binding.loadState.visibility = View.GONE
                    binding.successState.visibility = View.VISIBLE
                    adapter.setData(it.data)
                }
            }
        }
    }

    override fun openMovieCard(movie: Movie) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.example.geekbrainsmoviesapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.geekbrainsmoviesapp.R
import com.example.geekbrainsmoviesapp.databinding.FragmentFavoritesBinding
import com.example.geekbrainsmoviesapp.model.AppState
import com.example.geekbrainsmoviesapp.model.MovieDto
import com.example.geekbrainsmoviesapp.model.MoviesFilter
import com.example.geekbrainsmoviesapp.presentation.adapter.MoviesListAdapter
import com.example.geekbrainsmoviesapp.presentation.viewmodel.MoviesListViewModel
import com.example.geekbrainsmoviesapp.utils.hide
import com.example.geekbrainsmoviesapp.utils.show
import com.example.geekbrainsmoviesapp.utils.showSnack

class FavoritesFragment : Fragment() {
    private val viewModel: MoviesListViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MoviesListViewModel::class.java)
    }
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (savedInstanceState == null) {
            viewModel.setLiveDataAppState(AppState.Loading())
        }
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        val moviesListAdapter = moviesListAdapter()

        firstLaunch(savedInstanceState)

        watchAppState(moviesListAdapter)
    }

    private fun watchAppState(moviesListAdapter: MoviesListAdapter) {
        viewModel.getLiveDataAppState().observe(viewLifecycleOwner) {
            with(binding) {
                when (it) {
                    is AppState.Error<List<MovieDto>> -> {
                        errorState.show()
                        loadState.hide()
                        successState.hide()
                        errorState.showSnack(it.error.toString())
                    }
                    is AppState.Loading<List<MovieDto>> -> {
                        errorState.hide()
                        loadState.show()
                        successState.hide()
                    }
                    is AppState.Success<List<MovieDto>> -> {
                        errorState.hide()
                        loadState.hide()
                        successState.show()
                        moviesListAdapter.setData(it.data)
                    }
                }
            }

        }
    }

    private fun moviesListAdapter(): MoviesListAdapter {
        val moviesListAdapter = MoviesListAdapter {
            viewModel.setIdMovieOpen(it.id)
            navController.navigate(R.id.nav_details_movie)
        }

        with(binding.listContainer) {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = moviesListAdapter
        }
        return moviesListAdapter
    }

    private fun firstLaunch(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            viewModel.loadMovies(MoviesFilter.Favorites)
            viewModel.getMovies(MoviesFilter.Favorites)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

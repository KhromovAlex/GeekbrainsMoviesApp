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
import com.example.geekbrainsmoviesapp.databinding.MoviesListFragmentBinding
import com.example.geekbrainsmoviesapp.model.AppState
import com.example.geekbrainsmoviesapp.model.Movie
import com.example.geekbrainsmoviesapp.model.MoviesFilter
import com.example.geekbrainsmoviesapp.presentation.adapter.MoviesListAdapter
import com.example.geekbrainsmoviesapp.presentation.viewmodel.MoviesListViewModel
import com.example.geekbrainsmoviesapp.utils.hide
import com.example.geekbrainsmoviesapp.utils.show
import com.example.geekbrainsmoviesapp.utils.showSnack

class MoviesListFragment : Fragment() {

    private val viewModel: MoviesListViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MoviesListViewModel::class.java)
    }
    private var _binding: MoviesListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MoviesListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        val moviesListAdapter = MoviesListAdapter {
            viewModel.setMovieOpen(it)
            navController.navigate(R.id.nav_details_movie)
        }

        with(binding.listContainer) {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = moviesListAdapter
        }

        if (savedInstanceState == null) {
            viewModel.getMovies(MoviesFilter.All)
        }

        viewModel.getLiveDataAppState().observe(viewLifecycleOwner) {
            with(binding) {
                when (it) {
                    is AppState.Error<List<Movie>> -> {
                        errorState.show()
                        loadState.hide()
                        successState.hide()
                        errorState.showSnack(R.string.error)
                    }
                    is AppState.Loading<List<Movie>> -> {
                        errorState.hide()
                        loadState.show()
                        successState.hide()
                    }
                    is AppState.Success<List<Movie>> -> {
                        errorState.hide()
                        loadState.hide()
                        successState.show()
                        moviesListAdapter.setData(it.data)
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

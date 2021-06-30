package com.example.geekbrainsmoviesapp.presentation.view

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.*
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.geekbrainsmoviesapp.R
import com.example.geekbrainsmoviesapp.databinding.MoviesListFragmentBinding
import com.example.geekbrainsmoviesapp.model.AppState
import com.example.geekbrainsmoviesapp.model.MovieDto
import com.example.geekbrainsmoviesapp.model.MoviesFilter
import com.example.geekbrainsmoviesapp.presentation.adapter.MoviesListAdapter
import com.example.geekbrainsmoviesapp.presentation.viewmodel.MoviesListViewModel
import com.example.geekbrainsmoviesapp.utils.hide
import com.example.geekbrainsmoviesapp.utils.show
import com.example.geekbrainsmoviesapp.utils.showSnack

private const val IS_SHOW_ADULT_KEY = "IS_SHOW_ADULT_KEY"

class MoviesListFragment : Fragment() {
    private val viewModel: MoviesListViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MoviesListViewModel::class.java)
    }
    private var _binding: MoviesListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.adult_menu, menu)
        val checkBox: CheckBox = menu.findItem(R.id.action_adult).actionView as CheckBox
        checkBox.setText(R.string.adult)
        checkBox.isChecked = isShowAdult()
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            setShowAdult(isChecked)
            showAdultMovies(isChecked)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        if (savedInstanceState == null) {
            viewModel.setLiveDataAppState(AppState.Loading())
        }
        _binding = MoviesListFragmentBinding.inflate(inflater, container, false)
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

    private fun firstLaunch(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            viewModel.loadMovies(if (isShowAdult()) MoviesFilter.All else MoviesFilter.SkipAdult)
            viewModel.getMovies(if (isShowAdult()) MoviesFilter.All else MoviesFilter.SkipAdult)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isShowAdult(): Boolean =
        requireActivity().getPreferences(MODE_PRIVATE).getBoolean(IS_SHOW_ADULT_KEY, false)

    private fun setShowAdult(isAdult: Boolean) {
        requireActivity().getPreferences(MODE_PRIVATE)
            .edit()
            .putBoolean(IS_SHOW_ADULT_KEY, isAdult)
            .apply()
    }

    private fun showAdultMovies(isShow: Boolean) {
        if (isShow) {
            viewModel.loadMovies(MoviesFilter.All)
            viewModel.getMovies(MoviesFilter.All)
        } else {
            viewModel.loadMovies(MoviesFilter.SkipAdult)
            viewModel.getMovies(MoviesFilter.SkipAdult)
        }
    }

}

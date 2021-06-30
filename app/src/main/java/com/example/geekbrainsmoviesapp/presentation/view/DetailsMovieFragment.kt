package com.example.geekbrainsmoviesapp.presentation.view

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import com.example.geekbrainsmoviesapp.R
import com.example.geekbrainsmoviesapp.common.Common
import com.example.geekbrainsmoviesapp.common.Common.DETAILS_LOAD_RESULT_EXTRA
import com.example.geekbrainsmoviesapp.common.Common.DETAILS_REQUEST_DATA
import com.example.geekbrainsmoviesapp.common.Common.DETAILS_REQUEST_ID_EXTRA
import com.example.geekbrainsmoviesapp.common.Common.DETAILS_RESULT_SUCCESS
import com.example.geekbrainsmoviesapp.databinding.FragmentDetailsMovieBinding
import com.example.geekbrainsmoviesapp.model.AppState
import com.example.geekbrainsmoviesapp.model.MovieDto
import com.example.geekbrainsmoviesapp.presentation.viewmodel.MoviesListViewModel
import com.example.geekbrainsmoviesapp.services.DetailsService
import com.example.geekbrainsmoviesapp.utils.hide
import com.example.geekbrainsmoviesapp.utils.show
import com.example.geekbrainsmoviesapp.utils.showSnack
import java.text.SimpleDateFormat
import java.util.*


class DetailsMovieFragment : Fragment() {
    private lateinit var receiverDetails: BroadcastReceiver
    private val viewModel: MoviesListViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MoviesListViewModel::class.java)
    }
    private var _binding: FragmentDetailsMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (savedInstanceState == null) {
            viewModel.setLiveDataCurrentMovie(AppState.Loading())
        }
        _binding = FragmentDetailsMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBroadcastDetails()

        setOnTapFavoriteButton()

        setOnTapNoteButton()

        watchIdMovies(savedInstanceState)

        watchAppState()
    }

    private fun watchAppState() {
        viewModel.getLiveDataCurrentMovie().observe(viewLifecycleOwner) {
            with(binding) {
                when (it) {
                    is AppState.Error<MovieDto> -> {
                        errorState.show()
                        loadState.hide()
                        successState.hide()
                        errorState.showSnack(R.string.error)
                    }
                    is AppState.Loading<MovieDto> -> {
                        errorState.hide()
                        loadState.show()
                        successState.hide()
                    }
                    is AppState.Success<MovieDto> -> {
                        renderUi(it)
                    }
                }

            }

        }
    }

    private fun FragmentDetailsMovieBinding.renderUi(appState: AppState.Success<MovieDto>) {
        if (appState.data.isFavorites) {
            movieButtonToFavorites.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
        } else {
            movieButtonToFavorites.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
        }

        movieNoteText.setText(appState.data.note ?: "")
        movieTitle.text = appState.data.title
        movieTitleOriginal.text = appState.data.originalTitle
        movieDescription.text = appState.data.overview
        movieAdult.text = if (appState.data.adult) getString(
            R.string.placeholder_adult,
            getString(R.string.yes)
        ) else getString(R.string.placeholder_adult, getString(R.string.no))
        movieRuntime.text =
            getString(R.string.placeholder_time_min, appState.data.runtime)
        movieVoteAverage.text = appState.data.voteAverage.toString()
        movieVoteCount.text =
            getString(R.string.placeholder_vote_count, appState.data.voteCount)
        movieBudget.text = getString(R.string.placeholder_budget, appState.data.budget)
        movieRevenue.text = getString(R.string.placeholder_revenue, appState.data.revenue)
        movieReleaseDate.text = if (appState.data.releaseDate == null) "" else
            getString(
                R.string.placeholder_release_date,
                SimpleDateFormat(
                    "dd.MM.yyyy",
                    Locale("en")
                ).format(appState.data.releaseDate!!)
            )

        if (appState.data.posterPath == null || appState.data.posterPath!!.trim() == "") {
            movieImagePoster.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_baseline_close_24
                )
            )
            movieImagePoster.setBackgroundResource(R.color.gray)
        } else {
            Glide.with(movieImagePoster)
                .load("${Common.BASE_URL_IMAGE}${appState.data.posterPath}")
                .centerCrop()
                .into(movieImagePoster)
        }

        errorState.hide()
        loadState.hide()
        successState.show()
    }

    private fun watchIdMovies(savedInstanceState: Bundle?) {
        viewModel.getLiveDataCurrentIdMovie().observe(viewLifecycleOwner) {
            if (savedInstanceState == null) {
                requireContext()
                    .startService(Intent(requireContext(), DetailsService::class.java).apply {
                        putExtra(
                            DETAILS_REQUEST_ID_EXTRA,
                            it
                        )
                    })
            }
        }
    }

    private fun setBroadcastDetails() {
        receiverDetails = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)?.let {
                    when (it) {
                        DETAILS_RESULT_SUCCESS -> {
                            val data =
                                intent.getParcelableExtra<MovieDto>(DETAILS_REQUEST_DATA)
                            if (data == null) {
                                viewModel.setLiveDataCurrentMovie(
                                    AppState.Error(
                                        Exception(
                                            getString(
                                                R.string.error_load_data
                                            )
                                        )
                                    )
                                )
                            } else {
                                viewModel.updateMovie(data)
                            }

                        }
                        else -> {
                            viewModel.setLiveDataCurrentMovie(AppState.Error(Exception(getString(R.string.error_load_data))))
                        }
                    }
                }
            }
        }
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            receiverDetails,
            IntentFilter(Common.DETAILS_INTENT_FILTER)
        )
    }

    private fun setOnTapFavoriteButton() {
        binding.movieButtonToFavorites.setOnClickListener {
            if (viewModel.getLiveDataCurrentMovie().value is AppState.Success<MovieDto>) {
                val currentMovie =
                    (viewModel.getLiveDataCurrentMovie().value as AppState.Success<MovieDto>).data
                if (!currentMovie.isFavorites) {
                    binding.movieButtonToFavorites.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                    viewModel.toggleFavorites(currentMovie.apply { isFavorites = true })
                } else {
                    binding.movieButtonToFavorites.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                    viewModel.toggleFavorites(currentMovie.apply { isFavorites = false })
                }

            }
        }
    }

    private fun setOnTapNoteButton() {
        binding.movieNoteButton.setOnClickListener {
            if (viewModel.getLiveDataCurrentMovie().value is AppState.Success<MovieDto>) {
                hideKeyboardFrom(requireContext(), binding.movieNoteText)
                val currentMovie =
                    (viewModel.getLiveDataCurrentMovie().value as AppState.Success<MovieDto>).data
                viewModel.updateMovie(currentMovie.apply {
                    note = binding.movieNoteText.text.toString().trim()
                })
            }
        }
    }

    private fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }

    override fun onDestroyView() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiverDetails)
        super.onDestroyView()
        _binding = null
    }
}

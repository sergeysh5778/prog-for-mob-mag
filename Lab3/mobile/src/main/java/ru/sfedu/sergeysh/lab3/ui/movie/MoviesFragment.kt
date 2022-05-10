package ru.sfedu.sergeysh.lab3.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import ru.sfedu.sergeysh.lab3.R
import ru.sfedu.sergeysh.lab3.databinding.FragmentMoviesBinding

class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val movieViewModel: MovieViewModel by activityViewModels()

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        navController = findNavController()

        movieViewModel.movieList.observe(viewLifecycleOwner) {
            binding.progressIndicator.isGone = true

            it.isEmpty().apply {
                binding.emptyTextView.isGone = !this
                binding.movieListRecyclerView.isGone = this
            }

            (binding.movieListRecyclerView.adapter as MoviesAdapter).updateMovieList(it)
        }

        binding.movieListRecyclerView.adapter =
            MoviesAdapter(movieViewModel.movieList.value ?: listOf()) { movieUrl ->
                showMovieDetails(
                    movieUrl
                )
            }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showMovieDetails(movieUrl: String) {
        movieViewModel.setSelectedMovie(movieUrl)
        navController.navigate(R.id.action_navigate_to_selected_movie)
    }
}

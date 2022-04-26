package ru.sfedu.sergeysh.lab2.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.sfedu.sergeysh.lab2.databinding.FragmentMoviesBinding
import ru.sfedu.sergeysh.lab2.ui.MovieViewModel

class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val movieViewModel: MovieViewModel by activityViewModels()

        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.movieList.adapter =
            MoviesAdapter(arrayOf("Card1", "Card2", "Card3", "Card4", "Card5"))

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

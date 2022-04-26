package ru.sfedu.sergeysh.lab2.ui.selectedmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.sfedu.sergeysh.lab2.databinding.FragmentSelectedMovieBinding
import ru.sfedu.sergeysh.lab2.ui.MovieViewModel

class SelectedMovieFragment : Fragment() {

    private var _binding: FragmentSelectedMovieBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val movieViewModel: MovieViewModel by activityViewModels()
        /*val selectedMovieViewModel =
            ViewModelProvider(this).get(SelectedMovieViewModel::class.java)*/

        _binding = FragmentSelectedMovieBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSelectedMovie
        movieViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        /*selectedMovieViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package ru.sfedu.sergeysh.lab2.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textview.MaterialTextView
import ru.sfedu.sergeysh.lab2.databinding.FragmentSelectedMovieBinding

class SelectedMovieFragment : Fragment() {

    private var _binding: FragmentSelectedMovieBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val moviesViewModel: MovieViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectedMovieBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val emptyTextView: MaterialTextView = binding.emptyTextView
        val progressIndicator: CircularProgressIndicator = binding.progressIndicator
        val layout: LinearLayoutCompat = binding.layout

        val posterImageView: AppCompatImageView = binding.posterImageView
        val nameTextView: MaterialTextView = binding.nameTextView
        val genreTextView: MaterialTextView = binding.genreTextView
        val yearTextView: MaterialTextView = binding.yearTextView
        val actorsTextView: MaterialTextView = binding.actorsTextView
        val descriptionTextView: MaterialTextView = binding.descriptionTextView

        moviesViewModel.selectedMovie.observe(viewLifecycleOwner) {
            emptyTextView.isGone = true

            if (it == null) {
                progressIndicator.isGone = false
                layout.isGone = true
            } else {
                progressIndicator.isGone = true
                layout.isGone = false

                Glide.with(this).load(it.posterUrl).into(posterImageView)
                it.apply {
                    nameTextView.text = name
                    genreTextView.text = genre
                    yearTextView.text = year
                    actorsTextView.text = actors
                    descriptionTextView.text = description
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

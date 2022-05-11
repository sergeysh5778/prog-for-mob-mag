package ru.sfedu.sergeysh.lab3.ui.movie

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableLinearLayoutManager
import androidx.wear.widget.WearableRecyclerView
import ru.sfedu.sergeysh.lab3.databinding.ActivityMainBinding
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieViewModel: MovieViewModel by viewModels()
        val movieListRecyclerView: WearableRecyclerView = binding.movieListRecyclerView

        movieViewModel.movieList.observe(this) {
            binding.progressIndicator.isGone = true

            it.isEmpty().apply {
                binding.emptyTextView.isGone = !this
                movieListRecyclerView.isGone = this
            }

            (movieListRecyclerView.adapter as MoviesAdapter).updateMovieList(it)
        }

        movieListRecyclerView.apply {
            isEdgeItemsCenteringEnabled = true
            layoutManager = WearableLinearLayoutManager(applicationContext, object: WearableLinearLayoutManager.LayoutCallback() {

                private var progressToCenter: Float = 0F

                override fun onLayoutFinished(child: View, parent: RecyclerView) {
                    child.apply {
                        // Figure out % progress from top to bottom
                        val centerOffset = height.toFloat() / 2.0F / parent.height.toFloat()
                        val yRelativeToCenterOffset = y / parent.height + centerOffset

                        // Normalize for center and adjust to the maximum scale
                        progressToCenter = abs(0.5F - yRelativeToCenterOffset).coerceAtMost(0.65F)

                        (1 - progressToCenter).also {
                            scaleX = it
                            scaleY = it
                        }
                    }
                }
            })

            adapter = MoviesAdapter(movieViewModel.movieList.value ?: listOf())
        }
    }
}

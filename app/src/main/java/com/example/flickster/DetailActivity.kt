package com.example.flickster

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

const val MOVIE_EXTRA = "MOVIE_EXTRA" // Ensure this is defined

class DetailActivity : AppCompatActivity() {
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var posterImageView: ImageView
    private lateinit var releaseDateTextView: TextView
    private lateinit var ratingTextView: TextView
    private lateinit var popularityTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Find views by their IDs
        titleTextView = findViewById(R.id.titleTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        posterImageView = findViewById(R.id.posterImageView)
        releaseDateTextView = findViewById(R.id.releaseDateTextView)
        ratingTextView = findViewById(R.id.ratingTextView)
        popularityTextView = findViewById(R.id.popularityTextView)

        // Retrieve the movie data from the Intent
        val movie = intent.getSerializableExtra(MOVIE_EXTRA) as? Movie ?: return

        // Populate UI elements
        titleTextView.text = movie.title
        descriptionTextView.text = movie.overview
        releaseDateTextView.text = "Release: ${movie.release_date}"
        ratingTextView.text = "Rating: ${movie.vote_average}"
        popularityTextView.text = "Popularity: ${movie.popularity}"

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/${movie.poster_path}")
            .centerInside()
            .into(posterImageView)
    }
}


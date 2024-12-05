package com.example.flickster

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers

private const val TAG = "MoviesFragment"

class MoviesFragment : Fragment(), OnListFragmentInteractionListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)
        val progressBar = view.findViewById<ContentLoadingProgressBar>(R.id.progressBar)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        updateAdapter(progressBar, recyclerView)
        return view
    }

    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        val client = AsyncHttpClient()
        val apiUrl = "https://api.themoviedb.org/3/movie/now_playing?api_key=${BuildConfig.API_KEY}"

        client.get(apiUrl, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                progressBar.hide()
                val moviesRawJSON = json.jsonObject.getJSONArray("results").toString()
                val gson = Gson()
                val arrayMovieType = object : TypeToken<List<Movie>>() {}.type
                val models: List<Movie> = gson.fromJson(moviesRawJSON, arrayMovieType)

                recyclerView.adapter = MoviesRecyclerViewAdapter(models, this@MoviesFragment)
                Log.d(TAG, "Movies fetched successfully")
            }

            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, t: Throwable?) {
                progressBar.hide()
                Toast.makeText(requireContext(), "Failed to fetch movies.", Toast.LENGTH_LONG).show()
                Log.e(TAG, "Error: $errorResponse")
            }
        })
    }

    override fun onItemClick(item: Movie) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra("MOVIE_EXTRA", item) // Pass the Movie object to DetailActivity
        startActivity(intent)
    }
}

package com.example.flickster

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName("title") val title: String? = null,
    @SerialName("overview") val overview: String? = null,
    @SerialName("poster_path") val poster_path: String? = null,
    @SerialName("release_date") val release_date: String? = null,
    @SerialName("vote_average") val vote_average: String? = null,
    @SerialName("popularity") val popularity: String? = null
) : java.io.Serializable {
    val fullPosterPath: String
        get() = "https://image.tmdb.org/t/p/w500/$poster_path"
}
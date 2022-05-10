package ru.sfedu.sergeysh.lab3.data.movie

data class MovieDetails(
    val movieUrl: String,
    val posterUrl: String,
    val name: String,
    val genre: String,
    val year: String,
    val actors: String,
    val description: String,
)

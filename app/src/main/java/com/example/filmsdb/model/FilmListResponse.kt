package com.example.filmsdb.model

import com.google.gson.annotations.SerializedName

data class FilmListResponse (
    var page: Int,
    var results: List<FilmItem>,
    @SerializedName("total_results")
    var totalResults: Int,
    @SerializedName("total_pages")
    var totalPages: Int
)


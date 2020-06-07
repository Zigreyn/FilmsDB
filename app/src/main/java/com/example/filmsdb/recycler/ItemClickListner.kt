package com.example.filmsdb.recycler

import com.example.filmsdb.model.FilmItem


interface ItemClickListener {

    fun onItemClick(position: Int)

    fun onLikeClick(position: Int, isChecked: Boolean)

    fun onRemoveClick(position: Int, items: ArrayList<FilmItem>?)
}
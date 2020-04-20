package com.example.filmsdb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView

class FilmsAdapter(
    private val inflater: LayoutInflater,
private val items: List<FilmItem>,
private val clickListener: ItemClickListener
) : RecyclerView.Adapter<FilmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        return FilmViewHolder(inflater.inflate(R.layout.film_item, parent, false), clickListener)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
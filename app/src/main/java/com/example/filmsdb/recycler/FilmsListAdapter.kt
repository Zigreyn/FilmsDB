package com.example.filmsdb.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmsdb.R
import com.example.filmsdb.model.FilmItem

class FilmsListAdapter(
    private val inflater: LayoutInflater,
    private val items: List<FilmItem>?,
    private val clickListener: ItemClickListener?
) : RecyclerView.Adapter<FilmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        return FilmViewHolder(
            inflater.inflate(
                R.layout.film_item,
                parent,
                false
            ), clickListener
        )
    }

    override fun getItemCount() = items?.size ?: 0

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(items?.get(position))
    }
}
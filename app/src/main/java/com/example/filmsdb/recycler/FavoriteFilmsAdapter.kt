package com.example.filmsdb.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmsdb.model.FilmItem
import com.example.filmsdb.R

class FavoriteFilmsAdapter(
    private val inflater: LayoutInflater,
    private val items: List<FilmItem>?,
    private val clickListener: ItemClickListener?
) : RecyclerView.Adapter<FavoriteFilmsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteFilmsViewHolder {
        return FavoriteFilmsViewHolder(
            inflater.inflate(
                R.layout.favorite_item,
                parent,
                false
            ), clickListener
        )
    }

    override fun getItemCount() = items?.size ?: 0

    override fun onBindViewHolder(holder: FavoriteFilmsViewHolder, position: Int) {
        holder.bind(items?.get(position))
    }
}

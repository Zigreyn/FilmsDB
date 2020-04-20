package com.example.filmsdb

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FavoriteFilmsAdapter(
    private val inflater: LayoutInflater,
    private val items: List<FilmItem>,
    private val clickListener: ItemClickListener
) : RecyclerView.Adapter<FavoriteFilmsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteFilmsViewHolder {
        return FavoriteFilmsViewHolder(inflater.inflate(R.layout.favorite_item, parent, false), clickListener)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: FavoriteFilmsViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

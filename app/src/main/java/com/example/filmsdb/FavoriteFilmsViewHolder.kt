package com.example.filmsdb

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FavoriteFilmsViewHolder(itemView: View, private val clickListener: ItemClickListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private val filmImageView: ImageView = itemView.findViewById(R.id.filmImageView)
    private val filmHeader: TextView = itemView.findViewById(R.id.filmHeaderView)
    private val filmGenre: TextView = itemView.findViewById(R.id.filmGenreView)
    private val removeButton: ImageButton = itemView.findViewById(R.id.removeButton)

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        clickListener.onItemClick(adapterPosition, view)
    }

    fun bind(filmItem: FilmItem) {
        filmHeader.text = filmItem.name
        filmGenre.text = filmItem.genre
        filmImageView.setImageResource(filmItem.imageId)
        removeButton.setOnClickListener { clickListener.onRemoveClick(adapterPosition) }
    }
}

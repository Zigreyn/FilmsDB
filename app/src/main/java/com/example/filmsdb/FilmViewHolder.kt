package com.example.filmsdb

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView

class FilmViewHolder(itemView: View, private val clickListener: ItemClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private val filmImageView: ImageView = itemView.findViewById(R.id.filmImageView)
    private val filmHeader: TextView = itemView.findViewById(R.id.filmHeaderView)
    private val filmGenre: TextView = itemView.findViewById(R.id.filmGenreView)
    private val addFavoriteButton: ToggleButton = itemView.findViewById(R.id.addFavoriteButton)

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
        addFavoriteButton.isChecked = filmItem.isFavorite
        addFavoriteButton.setOnCheckedChangeListener { _, isChecked ->
            clickListener.onLikeClick(adapterPosition, isChecked)
        }
    }
}
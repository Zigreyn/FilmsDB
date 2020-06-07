package com.example.filmsdb.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.example.filmsdb.model.FilmItem
import com.example.filmsdb.R

class FilmViewHolder(itemView: View, private val clickListener: ItemClickListener?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private val filmImageView: ImageView = itemView.findViewById(R.id.filmImageView)
    private val filmHeader: TextView = itemView.findViewById(R.id.filmHeaderView)
    private val filmGenre: TextView = itemView.findViewById(R.id.filmGenreView)
    private val addFavoriteButton: ToggleButton = itemView.findViewById(R.id.addFavoriteButton)

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        clickListener?.onItemClick(adapterPosition)
    }

    fun bind(filmItem: FilmItem?) {
        filmHeader.text = filmItem?.name
        filmGenre.text = filmItem?.genre
        filmItem?.imageId?.let { filmImageView.setImageResource(it) }
        addFavoriteButton.isChecked = filmItem?.isFavorite ?: false

        addFavoriteButton.setOnCheckedChangeListener { _, isChecked ->
            clickListener?.onLikeClick(adapterPosition, isChecked)
        }
    }
}
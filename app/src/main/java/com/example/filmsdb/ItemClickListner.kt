package com.example.filmsdb

import android.view.View


interface ItemClickListener {

    fun onItemClick(position: Int, view: View)

    fun onLikeClick(position: Int, isChecked: Boolean)

    fun onRemoveClick(position: Int)
}
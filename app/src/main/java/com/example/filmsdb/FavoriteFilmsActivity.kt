package com.example.filmsdb

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_favorite_films.*

class FavoriteFilmsActivity : AppCompatActivity(), ItemClickListener{

    private lateinit var favoriteFilms: ArrayList<FilmItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_films)
        favoriteFilms = (intent.getParcelableArrayListExtra<FilmItem>(FAVORITE_FILMS))as ArrayList
        initRecycler()
    }

    private fun initRecycler(){
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val filmsAdapter = FavoriteFilmsAdapter(LayoutInflater.from(this), favoriteFilms, this)
        favoriteFilmsRecycler?.layoutManager = layoutManager
        favoriteFilmsRecycler?.adapter = filmsAdapter
    }

    override fun onItemClick(position: Int, view: View) {}

    override fun onLikeClick(position: Int, isChecked: Boolean) {}

    override fun onRemoveClick(position: Int) {
        favoriteFilms.removeAt(position)
        favoriteFilmsRecycler.adapter?.notifyItemRemoved(position)
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra(FAVORITE_FILMS, favoriteFilms)
        setResult(Activity.RESULT_OK, intent)
        super.onBackPressed()
    }

    companion object{
        const val FAVORITE_FILMS = "favorite_films"
        fun startFavoriteFilmsActivity(favoriteFilms: ArrayList<FilmItem>, activity: Activity) {
            val intent = Intent(activity, FavoriteFilmsActivity::class.java)
            intent.putExtra(FAVORITE_FILMS, favoriteFilms)
            activity.startActivityForResult(intent, MainActivity.FILM_FAVORITE_REQUEST_CODE)
        }
    }
}

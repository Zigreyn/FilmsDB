package com.example.filmsdb

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

private const val LAST_SELECTED_FILM = "last_selected_film"

class MainActivity : AppCompatActivity(), CustomDialog.IDialogCallback, ItemClickListener {

    private var lastSelectedFilm: Int = 0
    private lateinit var items: ArrayList<FilmItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        if (IS_DARK_MODE) setTheme(R.style.DarkTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        items = arrayListOf(
            FilmItem(
                getString(R.string.ep_1_header),
                getString(R.string.ep_1_description),
                R.drawable.episode_1,
                "Фантастика",
                false
            ),
            FilmItem(
                getString(R.string.ep_2_header),
                getString(R.string.ep_2_description),
                R.drawable.episode_2,
                "Фантастика",
                false
            ),
            FilmItem(
                getString(R.string.ep_3_header),
                getString(R.string.ep_3_description),
                R.drawable.episode_3,
                "Фантастика",
                false
            ),
            FilmItem(
                getString(R.string.ep_5_header),
                getString(R.string.ep_5_description),
                R.drawable.episode_5,
                "Фантастика",
                false
            ),
            FilmItem(
                getString(R.string.ep_7_header),
                getString(R.string.ep_7_description),
                R.drawable.episode_7,
                "Фантастика",
                false
            ),
            FilmItem(
                getString(R.string.ep_8_header),
                getString(R.string.ep_8_description),
                R.drawable.episode_8,
                "Фантастика",
                false
            )
        )

        initRecycler()

        nightMode?.isChecked = IS_DARK_MODE
        nightMode?.setOnClickListener { view ->
            view as Switch
            IS_DARK_MODE = view.isChecked
            recreate()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(LAST_SELECTED_FILM, lastSelectedFilm)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        lastSelectedFilm = savedInstanceState.getInt(LAST_SELECTED_FILM)
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILM_INFO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            var isLiked: Boolean? = null
            var filmComment: String? = null

            data?.let {
                isLiked = data.getBooleanExtra(IS_FILM_LIKED, false)
                filmComment = data.getStringExtra(FILM_COMMENT)
            }

            Log.d("Home Work", "Понравился фильм: $isLiked")
            Log.d("Home Work", "Комментрай к фильму: $filmComment")
        } else if (requestCode == FILM_FAVORITE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            val likedFilms: ArrayList<FilmItem> =
                (data?.getParcelableArrayListExtra<FilmItem>(FavoriteFilmsActivity.FAVORITE_FILMS)) as ArrayList
            revalidateFilmList(likedFilms)
            filmRecycler.adapter?.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favorite_films) {
            val filtered = items.filter { it.isFavorite }
            FavoriteFilmsActivity.startFavoriteFilmsActivity(filtered as ArrayList<FilmItem>, this)
        }
        return true
    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val filmsAdapter = FilmsAdapter(LayoutInflater.from(this), items, this)
        filmRecycler?.layoutManager = layoutManager
        filmRecycler?.adapter = filmsAdapter
        filmRecycler.addItemDecoration(
            ItemDecoration(
                this as Activity, 80,
                80
            )
        )
//        filmRecycler?.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun revalidateFilmList(favoriteFilms: ArrayList<FilmItem>) {
        items.forEach {
            if (!favoriteFilms.contains(it)) it.isFavorite = false
        }
    }

    override fun onBackPressed() {
        val dialog: Dialog = CustomDialog(this, getString(R.string.description_exit_conf), this)
        dialog.show()
    }

    override fun onClickPositiveButton() {
        finish()
    }

    override fun onClickNegativeButton() {}

    override fun onItemClick(position: Int, view: View) {
        FilmInfoActivity.startFilmDescriptionActivity(items[position], this)
    }

    override fun onLikeClick(position: Int, isChecked: Boolean) {
        items[position].isFavorite = isChecked
    }

    override fun onRemoveClick(position: Int) {}

    companion object {
        const val FILM_INFO_REQUEST_CODE = 1
        const val FILM_FAVORITE_REQUEST_CODE = 2
        const val IS_FILM_LIKED = "is_film_liked"
        const val FILM_COMMENT = "film_comment"
        var IS_DARK_MODE = false
    }
}

package com.example.filmsdb

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

private const val LAST_SELECTED_FILM = "last_selected_film"

class MainActivity : AppCompatActivity(), CustomDialog.IDialogCallback {

    private lateinit var ep5Header: TextView
    private lateinit var ep7Header: TextView
    private lateinit var ep8Header: TextView

    private var lastSelectedFilm: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (IS_DARK_MODE) setTheme(R.style.DarkTheme)

        setContentView(R.layout.activity_main)

        ep5Header = findViewById(R.id.ep_5_header)
        ep7Header = findViewById(R.id.ep_7_header)
        ep8Header = findViewById(R.id.ep_8_header)

        val ep5Button = findViewById<Button>(R.id.ep_5_button)
        ep5Button.setOnClickListener {
            lastSelectedFilm = 1
            updateFilmHeaderColor(lastSelectedFilm)
            startFilmDescriptionActivity(R.drawable.episode_5, R.string.ep_5_description, this)
        }

        val ep7Button = findViewById<Button>(R.id.ep_7_button)
        ep7Button.setOnClickListener {
            lastSelectedFilm = 2
            updateFilmHeaderColor(lastSelectedFilm)
            startFilmDescriptionActivity(R.drawable.episode_7, R.string.ep_7_description, this)
        }

        val ep8Button = findViewById<Button>(R.id.ep_8_button)
        ep8Button.setOnClickListener {
            lastSelectedFilm = 3
            updateFilmHeaderColor(lastSelectedFilm)
            startFilmDescriptionActivity(R.drawable.episode_8, R.string.ep_8_description, this)
        }

        nightMode?.setOnClickListener { view ->
            if (view is Switch) {
                IS_DARK_MODE = view.isChecked
            }
            recreate()
        }
    }

    private fun startFilmDescriptionActivity(
        imageId: Int,
        filmDescription: Int,
        activity: Activity
    ) {
        val intent = Intent(activity, FilmInfoActivity::class.java)
        intent.putExtra(IMAGE_ID, imageId)
        intent.putExtra(FILM_DESCRIPTION, filmDescription)
        startActivityForResult(intent, FILM_INFO_REQUEST_CODE)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(LAST_SELECTED_FILM, lastSelectedFilm)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        lastSelectedFilm = savedInstanceState.getInt(LAST_SELECTED_FILM)
        updateFilmHeaderColor(lastSelectedFilm)
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
        }
    }

    private fun updateFilmHeaderColor(header: Int) {
        when (header) {
            1 -> {
                ep5Header.setTextColor(Color.BLUE)
                ep7Header.setTextColor(Color.BLACK)
                ep8Header.setTextColor(Color.BLACK)
            }
            2 -> {
                ep5Header.setTextColor(Color.BLACK)
                ep7Header.setTextColor(Color.BLUE)
                ep8Header.setTextColor(Color.BLACK)
            }
            3 -> {
                ep5Header.setTextColor(Color.BLACK)
                ep7Header.setTextColor(Color.BLACK)
                ep8Header.setTextColor(Color.BLUE)
            }
        }
    }

    override fun onBackPressed() {
        val dialog: Dialog = CustomDialog(this, getString(R.string.description_exit_conf), this)
        dialog.show()
    }

    override fun onClickPositiveButton() {
        finish()
    }

    override fun onClickNegativeButton() {
    }

    companion object {
        const val FILM_INFO_REQUEST_CODE = 1
        const val IMAGE_ID = "image_id"
        const val FILM_DESCRIPTION = "film_description"
        const val IS_FILM_LIKED = "is_film_liked"
        const val FILM_COMMENT = "film_comment"
        var IS_DARK_MODE = false
    }
}

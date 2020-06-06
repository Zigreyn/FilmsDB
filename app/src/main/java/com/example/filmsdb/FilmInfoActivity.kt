package com.example.filmsdb

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class FilmInfoActivity : AppCompatActivity() {

    private var filmImage: Int = 0
    private var filmDescription: Int = 0
    private lateinit var filmLiked: CheckBox
    private lateinit var filmComment: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (MainActivity.IS_DARK_MODE) setTheme(R.style.DarkTheme)

        setContentView(R.layout.activity_film_info)

        filmImage = intent.getIntExtra(MainActivity.IMAGE_ID, 0)
        filmDescription = intent.getIntExtra(MainActivity.FILM_DESCRIPTION, 0)

        if (savedInstanceState != null) {
            filmImage = savedInstanceState.getInt(MainActivity.IMAGE_ID)
            filmDescription = savedInstanceState.getInt(MainActivity.FILM_DESCRIPTION)
        }
        updateFilmViews(filmImage, filmDescription)

        val buttonInvite = findViewById<ImageButton>(R.id.button_invite)
        buttonInvite.setOnClickListener {
            sendEmail(getString(R.string.invite_message))
        }

        filmLiked = findViewById(R.id.film_liked)
        filmComment = findViewById(R.id.film_comment)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(MainActivity.IMAGE_ID, filmImage)
        outState.putInt(MainActivity.FILM_DESCRIPTION, filmDescription)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        filmImage = savedInstanceState.getInt(MainActivity.IMAGE_ID)
        filmDescription = savedInstanceState.getInt(MainActivity.FILM_DESCRIPTION)
        updateFilmViews(filmImage, filmDescription)
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra(MainActivity.IS_FILM_LIKED, filmLiked.isChecked)
        intent.putExtra(MainActivity.FILM_COMMENT, filmComment.text.toString())
        setResult(Activity.RESULT_OK, intent)
        super.onBackPressed()
    }

    private fun updateFilmViews(filmImage: Int, filmDescription: Int) {

        if (filmImage != 0) {
            val filmImageView = findViewById<ImageView>(R.id.film_image)
            filmImageView.setImageResource(filmImage)
        }

        if (filmDescription != 0) {
            val filmDescriptionView = findViewById<TextView>(R.id.film_description)
            filmDescriptionView.setText(filmDescription)
        }
    }

    private fun sendEmail(textMessage: String) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, textMessage)
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "112233@gmail.com")
        sendIntent.type = "text/plain"
        sendIntent.resolveActivity(packageManager)?.let {
            startActivity(sendIntent)
        }
    }
}

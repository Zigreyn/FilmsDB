package com.example.filmsdb

import android.app.Dialog
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.ListFragment
import com.example.filmsdb.model.FilmItem
import com.example.filmsdb.recycler.ItemClickListener
import com.example.filmsdb.views.CustomDialog
import com.example.filmsdb.views.FilmInfoFragment
import com.example.filmsdb.views.FilmListFragment
import com.example.filmsdb.views.LikedFilmsFragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), CustomDialog.IDialogCallback,
    ItemClickListener, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var items: ArrayList<FilmItem>
    private var navigationView: NavigationView? = null
    private var drawer: DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        this.setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer?.addDrawerListener(toggle)
        toggle.syncState()

        navigationView = findViewById(R.id.nav_view)
        navigationView?.setNavigationItemSelectedListener(this)

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
        showFilmsFragment(items)
    }

    private fun revalidateFilmList(favoriteFilms: ArrayList<FilmItem>?) {
        items.forEach {
            if (favoriteFilms != null) {
                if (!favoriteFilms.contains(it)) {
                    it.isFavorite = false
                }
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            super.onBackPressed()
        } else {
            val dialog: Dialog = CustomDialog(
                this,
                getString(R.string.description_exit_conf),
                this
            )
            dialog.show()
        }
    }

    override fun onClickPositiveButton() {
        finish()
    }

    override fun onClickNegativeButton() {}

    override fun onItemClick(position: Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, FilmInfoFragment.newInstance(items[position]))
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .addToBackStack(null)
            .commit()
    }

    override fun onLikeClick(position: Int, isChecked: Boolean) {
        val fragment = supportFragmentManager.findFragmentByTag(FilmListFragment.TAG)
        if (fragment != null) {
            if (fragment.isVisible) {
                items[position].isFavorite = isChecked

                val snack = drawer?.let { Snackbar.make(it, "Успешно!", Snackbar.LENGTH_SHORT) }
                snack?.setAction("Отменить") {
                    items[position].isFavorite = !isChecked
                    (fragment as FilmListFragment).notifyAdapter(position)
                }
                snack?.show()
            }
        }
    }

    override fun onRemoveClick(position: Int, items: ArrayList<FilmItem>?) {
        revalidateFilmList(items)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_films -> showFilmsFragment(items)
            R.id.nav_liked -> {
                val filtered = items.filter { it.isFavorite }
                showLikedFilmsFragment(filtered as ArrayList<FilmItem>)
            }
        }
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showLikedFilmsFragment(items: ArrayList<FilmItem>) {
        val listFragment = LikedFilmsFragment.newInstance(items)
        listFragment.listener = this
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, listFragment, LikedFilmsFragment.TAG)
            .commit()
        navigationView?.setCheckedItem(R.id.nav_liked)
    }

    private fun showFilmsFragment(items: ArrayList<FilmItem>) {
        val listFragment = FilmListFragment.newInstance(items)
        listFragment.listener = this
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, listFragment, FilmListFragment.TAG)
            .commit()
        navigationView?.setCheckedItem(R.id.nav_films)
    }
}

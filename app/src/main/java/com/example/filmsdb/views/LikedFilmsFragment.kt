package com.example.filmsdb.views


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmsdb.R
import com.example.filmsdb.model.FilmItem
import com.example.filmsdb.recycler.FavoriteFilmsAdapter
import com.example.filmsdb.recycler.ItemClickListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_film_list.*
import kotlinx.android.synthetic.main.fragment_liked_films.*

/**
 * A simple [Fragment] subclass.
 */
class LikedFilmsFragment : Fragment(), ItemClickListener {

    private var items: ArrayList<FilmItem>? = null
    var listener: ItemClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            items = it.getParcelableArrayList(ITEMS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_liked_films, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler(){
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val filmsAdapter = FavoriteFilmsAdapter(
            LayoutInflater.from(activity), items, this
        )
        favoriteFilmsRecycler?.layoutManager = layoutManager
        favoriteFilmsRecycler?.adapter = filmsAdapter
        favoriteFilmsRecycler?.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        listener?.onRemoveClick( 0, items)
    }

    companion object {

        private const val ITEMS = "items"
        const val TAG = "LikedFilmFragment"

        @JvmStatic
        fun newInstance(items: ArrayList<FilmItem>) =
            LikedFilmsFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ITEMS, items)
                }
            }
    }

    override fun onItemClick(position: Int) {}

    override fun onLikeClick(position: Int, isChecked: Boolean) {}

    override fun onRemoveClick(position: Int, items: ArrayList<FilmItem>?) {
        val filmItem = this.items?.get(position)
        this.items?.removeAt(position)
        favoriteFilmsRecycler.adapter?.notifyItemRemoved(position)

        val snack = view?.let { Snackbar.make(it, "Успешно!", Snackbar.LENGTH_LONG) }
        snack?.setAction("Отменить") {
            filmItem?.let { it1 -> this.items?.add(position, it1) }
            favoriteFilmsRecycler.adapter?.notifyItemInserted(position)
        }
        snack?.show()
    }
}

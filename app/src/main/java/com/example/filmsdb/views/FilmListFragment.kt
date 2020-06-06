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
import com.example.filmsdb.recycler.FilmsListAdapter
import com.example.filmsdb.recycler.ItemClickListener
import kotlinx.android.synthetic.main.fragment_film_list.*
import java.text.FieldPosition


class FilmListFragment : Fragment() {

    private var items: ArrayList<FilmItem>? = null
    var listener: ItemClickListener? = null
    private lateinit var filmsAdapter: FilmsListAdapter

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
        return inflater.inflate(R.layout.fragment_film_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        filmsAdapter = FilmsListAdapter(
            LayoutInflater.from(activity),
            items,
            listener

        )
        filmRecycler?.layoutManager = layoutManager
        filmRecycler?.adapter = filmsAdapter
        filmRecycler.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    fun notifyAdapter(position: Int ){
        filmsAdapter.notifyItemChanged(position)
    }


    companion object {

        private const val ITEMS = "items"
        const val TAG = "FilmListFragment"

        @JvmStatic
        fun newInstance(items: ArrayList<FilmItem>) =
            FilmListFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ITEMS, items)
                }
            }
    }
}

package com.example.filmsdb.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmsdb.R
import com.example.filmsdb.model.FilmItem
import com.example.filmsdb.model.FilmListResponse
import com.example.filmsdb.recycler.FilmsListAdapter
import com.example.filmsdb.recycler.ItemClickListener
import kotlinx.android.synthetic.main.fragment_film_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.filmsdb.MovieApiClient


class FilmListFragment : Fragment() {

    private var items: ArrayList<FilmItem>? = null
    var listener: ItemClickListener? = null
    private lateinit var filmsAdapter: FilmsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
/*        arguments?.let {
            items = it.getParcelableArrayList(ITEMS)
        }*/
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


        val call = MovieApiClient.apiClient.getTopRatedMovies(API_KEY, "ru")

        call.enqueue(object : Callback<FilmListResponse> {
            override fun onResponse(
                call: Call<FilmListResponse>, response: Response<FilmListResponse>
            ) {
               items = response.body()!!.results as ArrayList<FilmItem>
                filmsAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<FilmListResponse>, t: Throwable) {
                Log.e(TAG, t.toString())
            }
        })

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

    fun notifyAdapter(position: Int) {
        filmsAdapter.notifyItemChanged(position)
    }


    companion object {

        private const val ITEMS = "items"
        const val TAG = "FilmListFragment"
        private const val API_KEY = "750ead228c9ee2670b957162f0cc62b5"

        @JvmStatic
        fun newInstance(items: ArrayList<FilmItem>) =
            FilmListFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ITEMS, items)
                }
            }
    }
}

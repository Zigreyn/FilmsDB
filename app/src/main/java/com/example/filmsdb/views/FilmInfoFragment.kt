package com.example.filmsdb.views


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.filmsdb.R
import com.example.filmsdb.model.FilmItem


class FilmInfoFragment : Fragment() {

    private var item: FilmItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = arguments?.getParcelable(ITEM)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_film_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val image = view.findViewById<ImageView>(R.id.image)
        item?.imageId?.let { image.setImageResource(it) }

        val toolbar = view.findViewById<Toolbar>(R.id.fragment_toolbar)
        toolbar.title = item?.name ?: "none"

        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val description = view.findViewById<TextView>(R.id.description)
        description.text = item?.description ?: "none"
    }

    companion object{

        private const val ITEM = "item"

        @JvmStatic
        fun newInstance(item: FilmItem) =
            FilmInfoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ITEM, item)
                }
            }
    }
}

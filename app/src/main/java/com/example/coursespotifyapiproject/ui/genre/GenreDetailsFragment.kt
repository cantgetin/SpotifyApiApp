package com.example.coursespotifyapiproject.ui.genre

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coursespotifyapiproject.R
import com.example.coursespotifyapiproject.di.utils.ViewModelFactory
import com.example.coursespotifyapiproject.ui.tracks.TracksFragmentArgs
import javax.inject.Inject

class GenreDetailsFragment @Inject constructor(
    viewModelFactory: ViewModelFactory
) : Fragment() {

    private lateinit var adapter: GenreDetailsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.genre_details_fragment, container, false)

        val genreTitle = view.findViewById<TextView>(R.id.genreTitle)
        val genreInfo = view.findViewById<TextView>(R.id.genreInfo)
        val genreImage = view.findViewById<ImageView>(R.id.genreImage)

        val args: GenreDetailsFragmentArgs by navArgs()
        val genre = args.genre

        genreTitle.text = genre.name
        genreInfo.text = genre.percent.toString()


        genreImage.setBackgroundColor(
            Color.parseColor(getColorCode(genre.name)))


//        view.apply {
//            recyclerView = view.findViewById(R.id.rView)
//            recyclerView.layoutManager = LinearLayoutManager(activity)
//            recyclerView = view.findViewById(R.id.rView)
//            adapter = GenreDetailsAdapter(arrayListOf(), itemClickListener)
//            adapter.addGenres(genres)
//            recyclerView.adapter = adapter
//        }

        return view
    }

    private fun getColorCode(inputString: String): String {
        return String.format("#%06x", 0xFFFFFF and inputString.hashCode())
    }

    private val itemClickListener: (String) -> Unit = {}

}
package com.example.coursespotifyapiproject.ui.track

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coursespotifyapiproject.R
import com.example.coursespotifyapiproject.data.model.Track
import com.example.coursespotifyapiproject.ui.tracks.TrackDetailsAdapter
import com.example.coursespotifyapiproject.ui.tracks.TracksAdapter
import com.example.coursespotifyapiproject.ui.tracks.TracksViewModel

class TrackDetailsFragment(val track: Track) : Fragment() {

    private lateinit var adapter: TrackDetailsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.track_details_fragment, container, false)

        val trackTitle = view.findViewById<TextView>(R.id.trackTitle)
        val trackArtists = view.findViewById<TextView>(R.id.trackArtists)
        val trackImage = view.findViewById<ImageView>(R.id.trackImage)

        trackTitle.text = track.title

        track.artists.forEachIndexed() { index, artist ->
            if (index == 0) trackArtists.append(artist.name)
            else trackArtists.append(" ,$artist.name")
        }

        val genres = mutableListOf<String>()

        track.artists.forEach() { artist ->
            artist.genres.forEachIndexed() { index, genre ->
                genres.add(genre)
            }
        }



        this.let { Glide.with(it).load(track.album.images[0].url).into(trackImage) };


        view.apply {
            recyclerView = view.findViewById(R.id.rView)
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView = view.findViewById(R.id.rView)
            adapter = TrackDetailsAdapter(arrayListOf(), itemClickListener)
            adapter.addGenres(genres)
            recyclerView.adapter = adapter
        }



        return view
    }

    val itemClickListener: (String) -> Unit = { track ->

    }


}
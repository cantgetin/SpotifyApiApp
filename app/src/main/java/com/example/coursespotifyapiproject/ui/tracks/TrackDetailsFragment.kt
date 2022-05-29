package com.example.coursespotifyapiproject.ui.tracks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.coursespotifyapiproject.R
import com.example.coursespotifyapiproject.data.model.Track
import com.google.android.material.imageview.ShapeableImageView

class TrackDetailsFragment(val track: Track) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.track_details_fragment, container, false)

            val trackTitle = view.findViewById<TextView>(R.id.trackTitle)
            val trackArtists = view.findViewById<TextView>(R.id.trackArtists)
            val trackImage = view.findViewById<ImageView>(R.id.trackImage)
            val trackGenres = view.findViewById<TextView>(R.id.genresText)

            trackTitle.text = track.title

            track.artists.forEachIndexed() { index, artist ->
                if (index == 0) trackArtists.append(artist.name)
                else trackArtists.append(" ,$artist.name")
            }

            track.artists.forEach() { artist ->
                artist.genres.forEachIndexed() { index, genre ->
                    if (index == 0) trackGenres.append(genre)
                    else trackGenres.append(" ,$genre")
                }
            }

            this.let { Glide.with(it).load(track.album.images[0].url).into(trackImage) };

        return view
    }


}
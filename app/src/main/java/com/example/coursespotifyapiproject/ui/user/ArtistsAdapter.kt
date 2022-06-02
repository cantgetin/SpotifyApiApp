package com.example.coursespotifyapiproject.ui.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coursespotifyapiproject.R
import com.example.coursespotifyapiproject.data.model.Artist

class ArtistsAdapter(
    private var artists: ArrayList<Artist>) : RecyclerView.Adapter<ArtistsAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(artist: Artist) {
            itemView.apply {

                val artistName = this.findViewById<TextView>(R.id.artistName)
                val artistGenres = this.findViewById<TextView>(R.id.artistGenres)
                val artistImage = this.findViewById<ImageView>(R.id.artistImage)

                artistName.text = artist.name
                artistGenres.text = artist.name
                this.let { Glide.with(it).load(artist.images[0].url).into(artistImage) }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.artist_item_layout, parent, false)
        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(artists[position])
    }

    fun addArtists(artists: List<Artist>) {
        this.artists = artists as ArrayList<Artist>
    }

    override fun getItemCount(): Int = this.artists.size


}
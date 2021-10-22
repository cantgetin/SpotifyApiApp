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
    private val artists: ArrayList<Artist>,
    val itemClickListener: (View, String) -> Unit
) : RecyclerView.Adapter<ArtistsAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

A
        fun bind(artist: Artist) {
            itemView.apply {

                val artistName = this.findViewById<TextView>(R.id.artistName)
                val artistGenres = this.findViewById<TextView>(R.id.artistGenres)
                val artistImage = this.findViewById<ImageView>(R.id.artistImage)

                artistName.text = artist.name
                artistGenres.text = artist.name
                this.let { Glide.with(it).load(artist.images[0].url).into(artistImage) };
            }
        }


        fun onClick(itemClickListener: (View, String) -> Unit) {}
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArtistsAdapter.DataViewHolder {

        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.artist_item_layout, parent, false)
        val vh = DataViewHolder(view);
        vh.onClick(itemClickListener)
        return vh
    }

    override fun onBindViewHolder(holder: ArtistsAdapter.DataViewHolder, position: Int) {
        holder.bind(artists[position])
        holder.itemView.setOnClickListener { view ->
            itemClickListener.invoke(view, artists[position].id)
        }
    }

    fun addArtists(artists: List<Artist>) {
        this.artists.apply {
            clear()
            addAll(artists)
        }
    }

    override fun getItemCount(): Int = artists.size


}
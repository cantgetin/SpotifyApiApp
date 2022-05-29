package com.example.coursespotifyapiproject.ui.tracks

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coursespotifyapiproject.R
import com.example.coursespotifyapiproject.data.model.Track
import com.example.coursespotifyapiproject.data.model.TrackItem
import com.google.android.material.imageview.ShapeableImageView
import kotlin.collections.ArrayList

class TracksAdapter(
    private val tracks: ArrayList<Track>,
    private val itemClickListener: (String) -> Unit
) : RecyclerView.Adapter<TracksAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(track: Track) {
            itemView.apply {
                val trackTitle = this.findViewById<TextView>(R.id.trackTitle)
                val trackArtists = this.findViewById<TextView>(R.id.trackArtists)
                val trackImage = this.findViewById<ImageView>(R.id.trackImage)
                val trackGenres = this.findViewById<TextView>(R.id.trackGenres)
                val genreColor = this.findViewById<ShapeableImageView>(R.id.genreColor)

                trackGenres.text = track.artists[0].genres[0]
                trackTitle.text = track.title

                track.artists.forEachIndexed { index, artist ->
                    if (index == 0) trackArtists.text = artist.name;
                    else trackArtists.text = String.format(trackArtists.text.toString()+", "+artist.name);
                }
                this.let { Glide.with(it).load(track.album.images[0].url).into(trackImage) };

                var color = Color.parseColor(getColorCode(track.artists[0].genres[0]));

                genreColor.setBackgroundColor(color);
            }
        }

        private fun getColorCode(inputString: String): String {
            return String.format("#%06x", 0xFFFFFF and inputString.hashCode())
        }

        fun onClick(itemClickListener: (String) -> Unit) {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksAdapter.DataViewHolder {

        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.track_item_layout, parent, false)
        val vh = DataViewHolder(view);
        vh.onClick(itemClickListener)
        return vh
    }

    override fun onBindViewHolder(holder: TracksAdapter.DataViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener { view ->
            itemClickListener.invoke(tracks[position].id)
        }
    }

    fun addTracks(tracks: List<TrackItem>) {
        tracks.forEach()
        {
            this.tracks.add(it.track)
        }
    }

    override fun getItemCount(): Int = tracks.size


}

package com.example.coursespotifyapiproject.ui.tracks

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coursespotifyapiproject.R
import kotlin.collections.ArrayList

class TrackDetailsAdapter(
    private val genres: ArrayList<String>,
    private val itemClickListener: (String) -> Unit
) : RecyclerView.Adapter<TrackDetailsAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(genre: String) {
            itemView.apply {
                val trackTitle = this.findViewById<TextView>(R.id.genreName)
                val genreColor = this.findViewById<ImageView>(R.id.genreImage)

                var color = Color.parseColor(getColorCode(genre));
                genreColor.setBackgroundColor(color)
                trackTitle.text = genre
            }
        }

        private fun getColorCode(inputString: String): String {
            return String.format("#%06x", 0xFFFFFF and inputString.hashCode())
        }

        fun onClick(itemClickListener: (String) -> Unit) {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackDetailsAdapter.DataViewHolder {

        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.genre_item_layout, parent, false)
        val vh = DataViewHolder(view);
        vh.onClick(itemClickListener)
        return vh
    }

    override fun onBindViewHolder(holder: TrackDetailsAdapter.DataViewHolder, position: Int) {
        holder.bind(genres[position])
        holder.itemView.setOnClickListener { view ->
            itemClickListener.invoke(genres[position])
        }
    }

    fun addGenres(tracks: List<String>) {
        tracks.forEach()
        {
            this.genres.add(it)
        }
    }

    override fun getItemCount(): Int = genres.size



}

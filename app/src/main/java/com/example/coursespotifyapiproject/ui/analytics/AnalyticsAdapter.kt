package com.example.coursespotifyapiproject.ui.analytics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coursespotifyapiproject.R
import java.util.*
import kotlin.collections.ArrayList
import android.graphics.Color
import com.example.coursespotifyapiproject.data.model.Genre


class AnalyticsAdapter(
    private val genres: ArrayList<Genre>,
    val itemClickListener: (String) -> Unit
) : RecyclerView.Adapter<AnalyticsAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(genre: Genre) {
            itemView.apply {

                val img = this.findViewById<ImageView>(R.id.genreImage)
                val str = this.findViewById<TextView>(R.id.genreName)
                val count = this.findViewById<TextView>(R.id.genreCount)


                str.text = genre.name
                count.text = "count " +genre.count.toString()

                val rnd = Random()
                val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))

                img.setBackgroundColor(color)


            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.genre_item_layout, parent, false)
        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(genres[position])
        holder.itemView.setOnClickListener {}
    }

    fun addGenres(genres: ArrayList<Genre>) {
        this.genres.apply {
            clear()
            addAll(genres)
        }
    }

    override fun getItemCount(): Int = genres.size


}
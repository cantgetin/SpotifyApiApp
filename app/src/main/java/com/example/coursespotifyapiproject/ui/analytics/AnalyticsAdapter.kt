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


class AnalyticsAdapter(
    private val genres: ArrayList<String>,
    val itemClickListener: (String) -> Unit
) : RecyclerView.Adapter<AnalyticsAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(genre: String) {
            itemView.apply {

                val img = this.findViewById<ImageView>(R.id.genreImage)
                val str = this.findViewById<TextView>(R.id.genreName)

                str.text = genre


                val rnd = Random()
                val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))

                img.setBackgroundColor(color)


            }
        }

        fun onClick(itemClickListener: (String) -> Unit) {}
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnalyticsAdapter.DataViewHolder {

        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.genre_item_layout, parent, false)
        val vh = DataViewHolder(view);
        vh.onClick(itemClickListener)
        return vh
    }

    override fun onBindViewHolder(holder: AnalyticsAdapter.DataViewHolder, position: Int) {
        holder.bind(genres[position])
        holder.itemView.setOnClickListener {}
    }

    fun addGenres(genres: List<String>) {
        this.genres.apply {
            clear()
            addAll(genres)
        }
    }

    override fun getItemCount(): Int = genres.size


}
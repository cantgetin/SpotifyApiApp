package com.example.coursespotifyapiproject.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Track (
    @SerializedName("artists")
    var artists: List<Artist>,
    @SerializedName("name")
    val title: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("album")
    val album: Album,

    ): Serializable
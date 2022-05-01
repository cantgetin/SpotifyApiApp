package com.example.coursespotifyapiproject.data.model

import com.google.gson.annotations.SerializedName

data class Track (
    @SerializedName("artist")
    val artist: Artist,
    @SerializedName("name")
    val title: String,
    @SerializedName("id")
    val id: String

)
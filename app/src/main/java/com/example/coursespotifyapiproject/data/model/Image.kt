package com.example.coursespotifyapiproject.data.model

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("height")
    val height: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: String,
)
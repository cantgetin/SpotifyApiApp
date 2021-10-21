package com.example.coursespotifyapiproject.data.model

import com.google.gson.annotations.SerializedName

data class Playlist(
    @SerializedName("name")
    val name: String,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("id")
    val id: String

)
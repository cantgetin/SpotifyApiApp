package com.example.coursespotifyapiproject.data.model

import com.google.gson.annotations.SerializedName

data class Artist(

    @SerializedName("name")
    val name: String,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("id")
    val id: String,
    @SerializedName("genres")
    var genres: List<String>
)
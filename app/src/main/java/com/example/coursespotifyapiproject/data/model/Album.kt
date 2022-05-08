package com.example.coursespotifyapiproject.data.model

import com.google.gson.annotations.SerializedName

data class Album(

    @SerializedName("name")
    val name: String,
    @SerializedName("images")
    val images: List<Image>,
)
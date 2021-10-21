package com.example.coursespotifyapiproject.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("country")
    val country: String,
    @SerializedName("display_name")
    val nickname: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("product")
    val product: String,
    @SerializedName("images")
    val images: List<Image>,
)


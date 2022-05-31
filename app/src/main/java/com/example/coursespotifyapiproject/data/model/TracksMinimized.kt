package com.example.coursespotifyapiproject.data.model

import com.google.gson.annotations.SerializedName

class TracksMinimized (
    @SerializedName("total")
    val total: Int,
    @SerializedName("href")
    val href: String
)
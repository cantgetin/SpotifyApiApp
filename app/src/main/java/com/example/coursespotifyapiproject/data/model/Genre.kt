package com.example.coursespotifyapiproject.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Genre(

    @SerializedName("name")
    var name: String,
    @SerializedName("count")
    var count: Int,
    @SerializedName("percent")
    var percent: Float,
): Serializable
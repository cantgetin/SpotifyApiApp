package com.example.coursespotifyapiproject.data.model

import com.google.gson.annotations.SerializedName

data class SpotifyComplexResponse(

    @SerializedName("items")
    val items: List<Playlist>

)
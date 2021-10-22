package com.example.coursespotifyapiproject.data.model

import com.google.gson.annotations.SerializedName

data class SpotifyComplexResponsePlaylists(

    @SerializedName("items")
    val items: List<Playlist>

)
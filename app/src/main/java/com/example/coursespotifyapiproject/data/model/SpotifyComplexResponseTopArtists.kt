package com.example.coursespotifyapiproject.data.model

import com.google.gson.annotations.SerializedName

data class SpotifyComplexResponseTopArtists(

    @SerializedName("items")
    val items: List<Artist>

)
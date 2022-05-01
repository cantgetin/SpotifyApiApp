package com.example.coursespotifyapiproject.data.model

import com.google.gson.annotations.SerializedName

data class SpotifyComplexResponseTracks(

    @SerializedName("items")
    val items: List<TrackItem>

)
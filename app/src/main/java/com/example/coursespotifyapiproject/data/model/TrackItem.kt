package com.example.coursespotifyapiproject.data.model

import com.google.gson.annotations.SerializedName

data class TrackItem (
    @SerializedName("track")
    val track: Track
)
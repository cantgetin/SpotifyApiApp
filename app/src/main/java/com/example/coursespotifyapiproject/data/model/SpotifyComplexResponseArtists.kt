package com.example.coursespotifyapiproject.data.model

import com.google.gson.annotations.SerializedName

data class SpotifyComplexResponseArtists(

    @SerializedName("artists")
    var artists: List<Artist>

)
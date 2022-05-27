package com.example.coursespotifyapiproject.data.model

import com.google.gson.annotations.SerializedName

data class AuthorizationResponse(

    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("scope")
    val scopes: String,
)
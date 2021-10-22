package com.example.coursespotifyapiproject.data.api

import com.example.coursespotifyapiproject.data.model.SpotifyComplexResponsePlaylists
import com.example.coursespotifyapiproject.data.model.SpotifyComplexResponseTopArtists
import com.example.coursespotifyapiproject.data.model.User
import retrofit2.http.GET
import retrofit2.http.Header


interface ApiService {

    @GET("me")
    suspend fun getUserData(@Header("Authorization") authorization: String?): User

    @GET("me/following")
    suspend fun getUserFollowing(@Header("Authorization") authorization: String?): User

    @GET("me/playlists")
    suspend fun getUserPlaylists(@Header("Authorization") authorization: String?): SpotifyComplexResponsePlaylists

    @GET("me/top/artists")
    suspend fun getUserTopArtists(@Header("Authorization") authorization: String?): SpotifyComplexResponseTopArtists

}
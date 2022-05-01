package com.example.coursespotifyapiproject.data.api

import com.example.coursespotifyapiproject.data.model.SpotifyComplexResponsePlaylists
import com.example.coursespotifyapiproject.data.model.SpotifyComplexResponseTopArtists
import com.example.coursespotifyapiproject.data.model.SpotifyComplexResponseTracks
import com.example.coursespotifyapiproject.data.model.User
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path


interface ApiService {

    @GET("me")
    suspend fun getUserData(@Header("Authorization") authorization: String?): User

    @GET("me/playlists")
    suspend fun getUserPlaylists(@Header("Authorization") authorization: String?): SpotifyComplexResponsePlaylists

    @GET("me/top/artists")
    suspend fun getUserTopArtists(@Header("Authorization") authorization: String?): SpotifyComplexResponseTopArtists

    @GET("playlists/{playlist_id}/tracks")
    suspend fun getUserTracks(
        @Header("Authorization") authorization: String?,
        @Path("playlist_id") playlistId: String
    ): SpotifyComplexResponseTracks

}
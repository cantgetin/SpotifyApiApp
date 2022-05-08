package com.example.coursespotifyapiproject.data.api

import com.example.coursespotifyapiproject.data.model.*
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("me")
    suspend fun getUserData(@Header("Authorization") authorization: String?): User

    @GET("me/playlists")
    suspend fun getUserPlaylists(@Header("Authorization") authorization: String?): SpotifyComplexResponsePlaylists

    @GET("me/top/artists")
    suspend fun getUserTopArtists(@Header("Authorization") authorization: String?): SpotifyComplexResponseTopArtists

    @GET("playlists/{playlist_id}/tracks")
    suspend fun getPlaylistTracks(
        @Header("Authorization") authorization: String?,
        @Path("playlist_id") playlistId: String
    ): SpotifyComplexResponseTracks

    @GET("artists")
    suspend fun getSeveralArtists(
        @Header("Authorization") authorization: String?,
        @Query("ids", encoded = true) ids: String
    ): SpotifyComplexResponseArtists

}
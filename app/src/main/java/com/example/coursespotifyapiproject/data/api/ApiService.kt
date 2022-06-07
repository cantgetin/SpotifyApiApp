package com.example.coursespotifyapiproject.data.api

import com.example.coursespotifyapiproject.data.model.*
import retrofit2.Call
import retrofit2.http.*


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

    @GET("me/tracks")
    suspend fun getUserLikedTracks(
        @Header("Authorization") authorization: String?
    ): SpotifyComplexResponseTracks

    @FormUrlEncoded
    @POST("https://accounts.spotify.com/api/token")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    fun getApiTokenByAuthCode(
        @Header("Authorization") authorization: String?,
        @Field("grant_type") grantType: String?,
        @Field("code") code: String?,
        @Field("redirect_uri") redirectUri: String?
    ): Call<AuthorizationResponse>

    @FormUrlEncoded
    @POST("https://accounts.spotify.com/api/token")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    fun getApiTokenByRefreshToken(
        @Header("Authorization") authorization: String?,
        @Field("grant_type") grantType: String?,
        @Field("refresh_token") refreshToken: String?,
    ): Call<AuthorizationResponse>
}
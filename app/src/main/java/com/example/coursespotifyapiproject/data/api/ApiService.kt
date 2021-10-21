package com.example.coursespotifyapiproject.data.api

import com.example.coursespotifyapiproject.data.model.User
import retrofit2.http.GET
import retrofit2.http.Header


interface ApiService {

    @GET("me")
    suspend fun getUserData(@Header("Authorization") authorization: String?): User

    @GET("me/following")
    suspend fun getUserFollowing(@Header("Authorization") authorization: String?): User

}
package com.example.coursespotifyapiproject.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getUser(authorization: String) = apiService.getUserData(authorization)

    suspend fun getUserFollowing(authorization: String) = apiService.getUserData(authorization)

}
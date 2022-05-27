package com.example.coursespotifyapiproject.data.api2

class ApiHelper2(private val apiService: ApiService2) {

    fun getApiTokenByAuthCode(
        authorization: String,
        grantType: String,
        code: String,
        redirectUri: String
    ) = apiService.getApiTokenByAuthCode(authorization, grantType, code, redirectUri)

    fun getApiTokenByRefreshToken(
        authorization: String,
        grantType: String,
        refreshToken: String
    ) = apiService.getApiTokenByRefreshToken(authorization, grantType, refreshToken)
}
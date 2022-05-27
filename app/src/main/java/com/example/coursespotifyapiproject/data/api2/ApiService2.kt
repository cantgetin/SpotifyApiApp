package com.example.coursespotifyapiproject.data.api2

import com.example.coursespotifyapiproject.data.model.*
import retrofit2.Call
import retrofit2.http.*


interface ApiService2 {

    @FormUrlEncoded
    @POST("api/token")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    fun getApiTokenByAuthCode(
        @Header("Authorization") authorization: String?,
        @Field("grant_type") grantType: String?,
        @Field("code") code: String?,
        @Field("redirect_uri") redirectUri: String?
    ): Call<AuthorizationResponse>

    @FormUrlEncoded
    @POST("api/token")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    fun getApiTokenByRefreshToken(
        @Header("Authorization") authorization: String?,
        @Field("grant_type") grantType: String?,
        @Field("refresh_token") refreshToken: String?,
    ): Call<AuthorizationResponse>

}
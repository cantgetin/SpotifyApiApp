package com.example.coursespotifyapiproject.data.api2

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder2 {

    private const val BASE_URL = "https://accounts.spotify.com/"

    val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor).build()

    private fun getRetrofit(): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService2 = getRetrofit().create(ApiService2::class.java)


}
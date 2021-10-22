package com.example.coursespotifyapiproject.di.modules

import com.example.coursespotifyapiproject.data.api.ApiHelper
import com.example.coursespotifyapiproject.data.api.RetrofitBuilder
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.Gson
import dagger.Provides
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import dagger.Module
import dagger.hilt.InstallIn

@Module
@InstallIn
class ApiModule {

    @Provides
    fun provideApiHelper(): ApiHelper = ApiHelper(RetrofitBuilder.apiService)


}
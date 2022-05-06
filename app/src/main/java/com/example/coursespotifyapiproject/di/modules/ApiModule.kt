package com.example.coursespotifyapiproject.di.modules

import com.example.coursespotifyapiproject.data.api.ApiHelper
import com.example.coursespotifyapiproject.data.api.RetrofitBuilder
import dagger.Provides
import dagger.Module

@Module
class ApiModule {

    @Provides
    fun provideApiHelper(): ApiHelper = ApiHelper(RetrofitBuilder.apiService)

}
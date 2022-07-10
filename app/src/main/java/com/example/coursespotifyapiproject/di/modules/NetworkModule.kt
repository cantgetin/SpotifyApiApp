package com.example.coursespotifyapiproject.di.modules

import com.example.coursespotifyapiproject.data.api.ApiHelper
import com.example.coursespotifyapiproject.data.api.RetrofitBuilder
import com.example.coursespotifyapiproject.data.db.Repository
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {

    @Provides
    fun provideApiHelper() : ApiHelper = ApiHelper(RetrofitBuilder.apiService)

    @Provides
    fun provideRepository() : Repository = Repository
}
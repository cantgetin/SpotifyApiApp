package com.example.coursespotifyapiproject.di.modules

import android.content.Context
import android.content.Context.MODE_PRIVATE
import dagger.Module
import android.content.SharedPreferences
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val context: Context) {

    @Provides
    fun provideContext() : Context {
        return context
    }

    @Provides
    @Singleton
    fun  provideSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences("spotify_api_app", MODE_PRIVATE)
    }
}
package com.example.coursespotifyapiproject.di.modules

import com.example.coursespotifyapiproject.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {
    @ContributesAndroidInjector
    fun contributeMainActivity(): MainActivity
}
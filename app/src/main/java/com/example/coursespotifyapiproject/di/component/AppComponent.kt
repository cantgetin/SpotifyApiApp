package com.example.coursespotifyapiproject.di.component

import com.example.coursespotifyapiproject.AuthenticationClient
import com.example.coursespotifyapiproject.MainActivity
import com.example.coursespotifyapiproject.di.modules.AppModule
import dagger.Component
import com.example.coursespotifyapiproject.di.modules.ApiModule
import javax.inject.Singleton

@Singleton
@Component(modules =[AppModule::class, ApiModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)

    fun inject(authenticationClient: AuthenticationClient)
}
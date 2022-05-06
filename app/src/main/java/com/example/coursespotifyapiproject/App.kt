package com.example.coursespotifyapiproject

import android.app.Application
import com.example.coursespotifyapiproject.di.component.AppComponent
import com.example.coursespotifyapiproject.di.component.DaggerAppComponent
import com.example.coursespotifyapiproject.di.modules.AppModule

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context = this))
            .build()
    }

}
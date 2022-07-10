package com.example.coursespotifyapiproject.di.component

import android.app.Application
import com.example.coursespotifyapiproject.AuthenticationClient
import com.example.coursespotifyapiproject.MainApplication
import com.example.coursespotifyapiproject.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        ViewModelModule::class,
        FragmentModule::class,
        NetworkModule::class
    ]
)
@Singleton
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun setApplication(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(mainApplication: MainApplication)

    fun inject(authenticationClient: AuthenticationClient)
}
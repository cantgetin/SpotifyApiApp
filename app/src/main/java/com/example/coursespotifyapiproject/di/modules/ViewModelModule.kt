package com.example.coursespotifyapiproject.di.modules

import androidx.lifecycle.ViewModel
import com.example.coursespotifyapiproject.di.utils.ViewModelKey
import com.example.coursespotifyapiproject.ui.analytics.AnalyticsViewModel
import com.example.coursespotifyapiproject.ui.playlists.PlaylistsViewModel
import com.example.coursespotifyapiproject.ui.tracks.TracksViewModel
import com.example.coursespotifyapiproject.ui.user.UserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    fun bindUserViewModel(viewModel: UserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlaylistsViewModel::class)
    fun bindPlaylistsViewModel(viewModel: PlaylistsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TracksViewModel::class)
    fun bindTracksViewModel(viewModel: TracksViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AnalyticsViewModel::class)
    fun bindAnalyticsViewModel(viewModel: AnalyticsViewModel): ViewModel

}
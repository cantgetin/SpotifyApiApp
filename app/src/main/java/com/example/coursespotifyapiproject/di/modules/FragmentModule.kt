package com.example.coursespotifyapiproject.di.modules

import androidx.fragment.app.Fragment
import com.example.coursespotifyapiproject.di.utils.FragmentKey
import com.example.coursespotifyapiproject.ui.analytics.AnalyticsFragment
import com.example.coursespotifyapiproject.ui.auth.AuthFragment
import com.example.coursespotifyapiproject.ui.genre.GenreDetailsFragment
import com.example.coursespotifyapiproject.ui.playlists.PlaylistsFragment
import com.example.coursespotifyapiproject.ui.track.TrackDetailsFragment
import com.example.coursespotifyapiproject.ui.tracks.TracksFragment
import com.example.coursespotifyapiproject.ui.user.UserFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface FragmentModule {

    @Binds
    @IntoMap
    @FragmentKey(AuthFragment::class)
    fun bindAuthFragment(fragment: AuthFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(UserFragment::class)
    fun bindUserFragment(fragment: UserFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(PlaylistsFragment::class)
    fun bindPlaylistsFragment(fragment: PlaylistsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(TracksFragment::class)
    fun bindTracksFragment(fragment: TracksFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(AnalyticsFragment::class)
    fun bindAnalytics(fragment: AnalyticsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(TrackDetailsFragment::class)
    fun bindTrackDetailsFragment(fragment: TrackDetailsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(GenreDetailsFragment::class)
    fun bindGenreDetailsFragment(fragment: GenreDetailsFragment): Fragment
}
package com.example.coursespotifyapiproject.ui.playlists

import androidx.lifecycle.ViewModel
import com.example.coursespotifyapiproject.data.db.Repository
import javax.inject.Inject

class PlaylistsViewModel @Inject constructor(repository: Repository) : ViewModel() {

    val userPlaylists = repository.userPlaylists
}
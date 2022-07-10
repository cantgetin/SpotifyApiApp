package com.example.coursespotifyapiproject.ui.tracks

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.coursespotifyapiproject.data.db.Repository
import com.example.coursespotifyapiproject.data.model.Track
import com.example.coursespotifyapiproject.utils.Resource
import javax.inject.Inject

class TracksViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val likedTracks : LiveData<Resource<MutableList<Track>>> = repository.likedTracks

    fun getTracks(playlistId: String) : LiveData<Resource<MutableList<Track>>> {
        return repository.getTracks(playlistId)
    }
}
package com.example.coursespotifyapiproject.ui.tracks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.coursespotifyapiproject.data.api.ApiHelper
import com.example.coursespotifyapiproject.data.api.RetrofitBuilder
import com.example.coursespotifyapiproject.SpotifyConstants
import com.example.coursespotifyapiproject.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class TracksViewModel @Inject constructor(private val apiHelper : ApiHelper) : ViewModel() {

    private val accessToken = SpotifyConstants.TOKEN


    fun getTracks(playlistId: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.getPlaylistTracksWithGenres("Bearer $accessToken", playlistId, false)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getLikedTracks() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.getPlaylistTracksWithGenres("Bearer $accessToken", "", true)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


}
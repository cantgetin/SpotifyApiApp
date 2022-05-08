package com.example.coursespotifyapiproject.ui.tracks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.coursespotifyapiproject.data.api.ApiHelper
import com.example.coursespotifyapiproject.data.api.RetrofitBuilder
import com.example.coursespotifyapiproject.utils.Resource
import com.example.spotifysigninexample.SpotifyConstants
import kotlinx.coroutines.Dispatchers

class TracksViewModel : ViewModel() {

    private val apiHelper = ApiHelper(RetrofitBuilder.apiService)
    private val accessToken = SpotifyConstants.TOKEN


    fun getTracks(playlistId: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.getPlaylistTracksWithGenres("Bearer $accessToken", playlistId)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


}
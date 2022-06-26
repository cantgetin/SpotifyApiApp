package com.example.coursespotifyapiproject.ui.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.coursespotifyapiproject.data.api.ApiHelper
import com.example.coursespotifyapiproject.SpotifyConstants
import com.example.coursespotifyapiproject.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class AnalyticsViewModel @Inject constructor(private val apiHelper : ApiHelper) : ViewModel() {

    private val accessToken = SpotifyConstants.TOKEN

    fun getPlaylists() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.getUserTopArtists("Bearer $accessToken")))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}


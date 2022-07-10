package com.example.coursespotifyapiproject.data.db

import androidx.lifecycle.liveData
import com.example.coursespotifyapiproject.SpotifyConstants
import com.example.coursespotifyapiproject.data.api.ApiHelper
import com.example.coursespotifyapiproject.data.api.RetrofitBuilder
import com.example.coursespotifyapiproject.utils.Resource
import kotlinx.coroutines.Dispatchers

object Repository {

    private val accessToken = SpotifyConstants.TOKEN
    private val apiHelper = ApiHelper(RetrofitBuilder.apiService)

    val userInfo = getUserInfoFromApi()
    val userTopArtists = getUserTopArtistsFromApi()
    val likedTracks = getLikedTracksFromApi()
    val userPlaylists = getUserPlaylistsFromApi()
    val userAnalytics = getAnalyticsFromApi()
    fun getTracks(playlistId: String) = getTracksFromApi(playlistId)



    private fun getUserInfoFromApi() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.getUser("Bearer $accessToken")))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    private fun getUserTopArtistsFromApi() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.getUserTopArtists("Bearer $accessToken")))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    private fun getTracksFromApi(playlistId: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.getPlaylistTracksWithGenres("Bearer $accessToken", playlistId, false)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    private fun getLikedTracksFromApi() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.getPlaylistTracksWithGenres("Bearer $accessToken", "", true)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    private fun getUserPlaylistsFromApi() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.getUserPlaylists("Bearer $accessToken")))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    private fun getAnalyticsFromApi() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.getUserTopArtists("Bearer $accessToken")))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}
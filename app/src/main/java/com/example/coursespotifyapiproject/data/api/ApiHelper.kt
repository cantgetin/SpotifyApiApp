package com.example.coursespotifyapiproject.data.api

import android.util.Log
import com.example.coursespotifyapiproject.data.model.SpotifyComplexResponseArtists
import com.example.coursespotifyapiproject.data.model.SpotifyComplexResponseTracks
import com.example.coursespotifyapiproject.data.model.Track

class ApiHelper(private val apiService: ApiService) {

    suspend fun getUser(authorization: String) = apiService.getUserData(authorization)

    suspend fun getUserPlaylists(authorization: String) = apiService.getUserPlaylists(authorization)

    suspend fun getUserTopArtists(authorization: String) =
        apiService.getUserTopArtists(authorization)

    suspend fun getPlaylistTracksWithGenres(
        authorization: String,
        playlistId: String
    ): MutableList<Track> {

        var tracks = apiService.getPlaylistTracks(authorization, playlistId)
        var artistIdsList: List<String> = emptyList()

        try {
            tracks.items.forEachIndexed() { index, trackItem ->
                trackItem.track.artists.forEach() { artist ->
                    artistIdsList = artistIdsList.plusElement(artist.id);
                }
            }
        } catch (e: Exception) {
            Log.d("", "Exception while getting artists IDs from tracks$e")
        }

        val artistsFull = SpotifyComplexResponseArtists(emptyList())
        val sampleGenres = arrayListOf("pop")

        try {
            for (i in 0..artistIdsList.count() step 50) {
                var tillTheItem = i + 50;
                if (tillTheItem > artistIdsList.count()) tillTheItem = artistIdsList.count()

                artistsFull.artists = artistsFull.artists +
                        (apiService.getSeveralArtists(
                            authorization,
                            artistIdsList.subList(i, tillTheItem).toString()
                                .replace(" ", "")
                                .replace("[", "")
                                .replace("]", "")
                        )).artists

            }
        } catch (e: Exception) {
            Log.d("", "Exception while getting artists$e")
        }


        var lol = mutableListOf<Track>()
        tracks.items.forEach{
            lol.add(it.track)
        }

        lol = lol.filterNotNull() as MutableList<Track>

        try {
            lol.forEach() { track ->
                track.artists.forEach() { artist ->
                    val fullArtistGenres = artistsFull.artists.find { it.id == artist.id }?.genres

                    if (fullArtistGenres != null) {
                        if (fullArtistGenres.isNotEmpty()) artist.genres = fullArtistGenres
                        else artist.genres = sampleGenres
                    }
                    else artist.genres = sampleGenres
                }
            }
        } catch (e: Exception) {
            Log.d("", "Exception while assigning artist genres$e")
        }

        return  lol
    }

    fun getApiTokenByAuthCode(
        authorization: String,
        grantType: String,
        code: String,
        redirectUri: String
    ) = apiService.getApiTokenByAuthCode(authorization, grantType, code, redirectUri)

    fun getApiTokenByRefreshToken(
        authorization: String,
        grantType: String,
        refreshToken: String
    ) = apiService.getApiTokenByRefreshToken(authorization, grantType, refreshToken)

}
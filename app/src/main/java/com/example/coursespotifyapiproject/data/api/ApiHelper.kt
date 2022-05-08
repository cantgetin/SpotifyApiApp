package com.example.coursespotifyapiproject.data.api

import com.example.coursespotifyapiproject.data.model.SpotifyComplexResponseTracks

class ApiHelper(private val apiService: ApiService) {

    suspend fun getUser(authorization: String) = apiService.getUserData(authorization)

    suspend fun getUserPlaylists(authorization: String) = apiService.getUserPlaylists(authorization)

    suspend fun getUserTopArtists(authorization: String) = apiService.getUserTopArtists(authorization)

    suspend fun getPlaylistTracksWithGenres(authorization: String, playlistId: String): SpotifyComplexResponseTracks {

        val tracks = apiService.getPlaylistTracks(authorization, playlistId)
        var artistIds = ""

        tracks.items.forEachIndexed() { index,trackItem ->
            trackItem.track.artists.forEach() { artist ->
                if (index == 0) artistIds = artist.id
                else artistIds = artistIds + ","+artist.id
            }
        }

        val artistsFull = apiService.getSeveralArtists(authorization, artistIds)
        val sampleGenres = arrayListOf("pop")

        tracks.items.forEach() { trackItem ->
            for(item in trackItem.track.artists)
            trackItem.track.artists.forEach() { artist ->
                val fullArtistGenres = artistsFull.artists.find{ it.id == artist.id}?.genres
                if (fullArtistGenres != null) {
                    if (fullArtistGenres.isNotEmpty()) artist.genres = fullArtistGenres
                    else artist.genres = sampleGenres
                }
            }
        }

        return tracks;
    }

    suspend fun getSeveralArtists(authorization: String, ids: Array<String>) = apiService.getSeveralArtists(authorization, ids.toString())

}
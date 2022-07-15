package com.example.coursespotifyapiproject.data.api

import com.example.coursespotifyapiproject.data.model.*

class ApiHelper(private val apiService: ApiService) {

    suspend fun getUser(authorization: String) = apiService.getUserData(authorization)

    suspend fun getUserPlaylists(authorization: String) = apiService.getUserPlaylists(authorization)

    suspend fun getUserTopArtists(authorization: String) =
        apiService.getUserTopArtists(authorization)

    suspend fun getAnalyticsFromApi(authorization: String): List<Genre> {

        val response = apiService.getUserTopArtists(authorization)
        val genreNames: ArrayList<String> = ArrayList()
        var genres: ArrayList<Genre> = ArrayList()

        response.items.forEach { artist ->
            artist.genres.forEach { genreName ->
                genreNames.add(genreName)
            }
        }

        genreNames.forEach { element ->
            var genre = Genre(element, genreNames.count { it == element}, 0f)
            genres.add(genre)
        }

        genres = genres.distinct() as ArrayList<Genre>


        val percent : Float = (genres.count() / 100f).toFloat()

        genres.forEach { element ->
            element.percent = ((element.count / percent) / 1.0).toFloat()
        }

        return genres.distinct().sortedWith(compareByDescending { it.percent })
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

    suspend fun getPlaylistTracksWithGenres(
        authorization: String,
        playlistId: String,
        likedTracks: Boolean
    ): MutableList<Track> {

        val tracks: SpotifyComplexResponseTracks =
            if (likedTracks) apiService.getUserLikedTracks(authorization)
        else apiService.getPlaylistTracks(authorization, playlistId)


        var artistIdsList: List<String> = emptyList()

        try {
            tracks.items.forEach { trackItem ->
                trackItem.track.artists.forEach { artist ->
                    artistIdsList = artistIdsList.plusElement(artist.id)
                }
            }
        } catch (e: Exception) {
            //Toast.makeText(context, "Exception while getting artists IDs from tracks $e", Toast.LENGTH_LONG).show()
        }

        val artistsFullInfo = SpotifyComplexResponseArtists(emptyList())
        val sampleGenres = arrayListOf("pop")

        try {
            for (i in 0..artistIdsList.count() step 50) {
                var tillTheItem = i + 50
                if (tillTheItem > artistIdsList.count()) tillTheItem = artistIdsList.count()

                artistsFullInfo.artists = artistsFullInfo.artists +
                        (apiService.getSeveralArtists(
                            authorization,
                            artistIdsList.subList(i, tillTheItem).toString()
                                .replace(" ", "")
                                .replace("[", "")
                                .replace("]", "")
                        )).artists

            }
        } catch (e: Exception) {
            //Toast.makeText(context, "Exception while getting artist genres $e", Toast.LENGTH_LONG).show()
        }

        var result = mutableListOf<Track>()
        tracks.items.forEach {
            result.add(it.track)
        }

        result = result.filterNotNull() as MutableList<Track>

        try {
            result.forEach { track ->
                track.artists.forEach { artist ->
                    val fullArtistGenres = artistsFullInfo.artists.find { it.id == artist.id }?.genres

                    if (fullArtistGenres != null) {
                        if (fullArtistGenres.isNotEmpty()) artist.genres = fullArtistGenres
                        else artist.genres = sampleGenres
                    } else artist.genres = sampleGenres
                }
            }
        } catch (e: Exception) {
            //Toast.makeText(context, "Exception while assigning artist genres $e", Toast.LENGTH_LONG).show()
        }

        return result
    }



}
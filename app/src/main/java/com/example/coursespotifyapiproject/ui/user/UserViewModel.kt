package com.example.coursespotifyapiproject.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.coursespotifyapiproject.data.db.Repository
import com.example.coursespotifyapiproject.data.model.SpotifyComplexResponseTopArtists
import com.example.coursespotifyapiproject.data.model.User
import com.example.coursespotifyapiproject.utils.Resource
import javax.inject.Inject

class UserViewModel @Inject constructor(repository: Repository) : ViewModel() {

    val userInfo : LiveData<Resource<User>> = repository.userInfo
    val userTopArtists : LiveData<Resource<SpotifyComplexResponseTopArtists>> = repository.userTopArtists

}
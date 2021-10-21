package com.example.coursespotifyapiproject.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Dispatchers
import android.widget.Toast
import androidx.lifecycle.liveData
import com.bumptech.glide.Glide
import com.example.coursespotifyapiproject.R
import com.example.coursespotifyapiproject.data.api.ApiHelper
import com.example.coursespotifyapiproject.data.api.RetrofitBuilder
import com.example.coursespotifyapiproject.utils.Resource
import com.example.coursespotifyapiproject.utils.Status
import com.example.spotifysigninexample.SpotifyConstants
import kotlinx.android.synthetic.main.user_fragment.*


class UserFragment() : Fragment() {

    private val apiHelper = ApiHelper(RetrofitBuilder.apiService)
    private val accessToken = SpotifyConstants.TOKEN

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fetchUserProfile()
        return inflater.inflate(R.layout.user_fragment, container, false)
    }


    private fun fetchUserProfile() {
        getUser().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    resource.data?.let { user ->
                        progressBar.visibility = View.GONE

                        userName.visibility = View.VISIBLE
                        userCountry.visibility = View.VISIBLE
                        userProduct.visibility = View.VISIBLE
                        userId.visibility = View.VISIBLE
                        userAvatar.visibility = View.VISIBLE


                        userName.text = ("Hey, " + user.nickname)
                        userCountry.text = ("Country: " + user.country)
                        userProduct.text = ("Product: " + user.product)
                        userId.text = ("User id: " + user.id)
                        view?.let { Glide.with(it).load(user.images[0].url).into(userAvatar) };

                    }
                }
                Status.ERROR -> {

                    Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE

                    userName.visibility = View.GONE
                    userCountry.visibility = View.GONE
                    userProduct.visibility = View.GONE
                    userId.visibility = View.GONE
                }
            }
        }
    }

    private fun getUser() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.getUser("Bearer $accessToken")))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}




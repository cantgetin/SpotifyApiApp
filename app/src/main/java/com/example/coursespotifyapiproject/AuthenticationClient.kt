package com.example.coursespotifyapiproject

import android.app.Activity
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.coursespotifyapiproject.data.model.AuthorizationResponse
import com.example.coursespotifyapiproject.ui.auth.AuthFragment
import com.example.spotifysigninexample.SpotifyConstants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import androidx.fragment.app.FragmentActivity
import com.example.coursespotifyapiproject.data.api.ApiHelper
import com.example.coursespotifyapiproject.data.api.RetrofitBuilder


class AuthenticationClient(val userAuthenticated: () -> Unit) {

    @Inject
    lateinit var pref: SharedPreferences

    private val apiHelper = ApiHelper(RetrofitBuilder.apiService)
    lateinit var activity: Activity

    fun connect(savedInstanceState: Bundle?, activity: Activity) {

        this.activity = activity
        (activity.applicationContext as App).appComponent.inject(this)
        if (userHasAuthCodeStored()) {
            SpotifyConstants.CODE = pref.getString("spotify_auth_code", null).toString()
            if (userHasRefreshTokenStored()) {
                SpotifyConstants.REFRESHTOKEN =
                    pref.getString("spotify_refresh_token", null).toString()
                getApiTokenByRefreshToken()
            } else getApiTokenByAuthCode()
        } else if (!userHasAuthCodeStored()) startUserAuthentication(savedInstanceState)
    }

    private fun getApiTokenByAuthCode() {

        val authorizationParam = SpotifyConstants.CLIENT_ID + ":" + SpotifyConstants.CLIENT_SECRET
        val authorizationParamEncoded: String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            authorizationParamEncoded =
                Base64.getEncoder().encodeToString(authorizationParam.toByteArray())
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        Log.d("fucking", SpotifyConstants.CODE)

        val retrofitData = apiHelper.getApiTokenByAuthCode(
            "Basic $authorizationParamEncoded",
            "authorization_code",
            SpotifyConstants.CODE,
            SpotifyConstants.REDIRECT_URI
        )

        var token: String?
        var refreshToken: String?

        retrofitData.enqueue(object : Callback<AuthorizationResponse?> {
            override fun onResponse(
                call: Call<AuthorizationResponse?>,
                response: Response<AuthorizationResponse?>
            ) {
                token = response.body()?.accessToken ?: ""
                refreshToken = response.body()?.refreshToken ?: ""

                if (token != "" && refreshToken != "") {
                    SpotifyConstants.TOKEN = token as String
                    SpotifyConstants.REFRESHTOKEN = refreshToken as String


                    val edit: SharedPreferences.Editor = pref.edit()
                    edit.putString("spotify_api_token", token)
                    edit.putString("spotify_refresh_token", refreshToken)
                    edit.apply()
                    userAuthenticated()
                }

            }

            override fun onFailure(call: Call<AuthorizationResponse?>, t: Throwable) {
                Toast.makeText(activity.applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getApiTokenByRefreshToken() {
        val authorizationParam = SpotifyConstants.CLIENT_ID + ":" + SpotifyConstants.CLIENT_SECRET
        val authorizationParamEncoded: String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            authorizationParamEncoded =
                Base64.getEncoder().encodeToString(authorizationParam.toByteArray())
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        Log.d("fucking", SpotifyConstants.CODE)

        val retrofitData = apiHelper.getApiTokenByRefreshToken(
            "Basic $authorizationParamEncoded",
            "refresh_token",
            SpotifyConstants.REFRESHTOKEN,
        )

        var token: String?

        retrofitData.enqueue(object : Callback<AuthorizationResponse?> {
            override fun onResponse(
                call: Call<AuthorizationResponse?>,
                response: Response<AuthorizationResponse?>
            ) {
                token = response.body()?.accessToken ?: ""

                if (token != "") {
                    SpotifyConstants.TOKEN = token as String

                    val edit: SharedPreferences.Editor = pref.edit()
                    edit.putString("spotify_api_token", token)
                    edit.apply()
                    userAuthenticated()
                }

            }

            override fun onFailure(call: Call<AuthorizationResponse?>, t: Throwable) {
                Toast.makeText(activity.applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun startUserAuthentication(savedInstanceState: Bundle?) {

        val ft = (activity as FragmentActivity).supportFragmentManager

        if (savedInstanceState == null) {
            ft.beginTransaction()
                .replace(R.id.container, AuthFragment())
                .commitNow()
        }
    }

    private fun userHasAuthCodeStored(): Boolean {
        return pref.getString("spotify_auth_code", null) != null
    }

    private fun userHasRefreshTokenStored(): Boolean {
        return pref.getString("spotify_refresh_token", null) != null
    }
}
package com.example.coursespotifyapiproject

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.coursespotifyapiproject.data.api.ApiHelper
import com.example.coursespotifyapiproject.data.api.RetrofitBuilder
import com.example.coursespotifyapiproject.data.model.AuthorizationResponse
import com.example.coursespotifyapiproject.di.component.AppComponent
import com.example.coursespotifyapiproject.di.component.DaggerAppComponent
import com.example.coursespotifyapiproject.di.modules.AppModule
import dagger.android.support.DaggerAppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject


class AuthenticationClient(val userAuthenticated: () -> Unit, private var activity: Activity) {


    lateinit var pref: SharedPreferences

    private val apiHelper = ApiHelper(RetrofitBuilder.apiService)

    fun authenticate() {

        pref = activity.applicationContext.getSharedPreferences("spotify_api_app",
            DaggerAppCompatActivity.MODE_PRIVATE
        )

        if (userHasAuthCodeStored()) {
            SpotifyConstants.CODE = pref.getString("spotify_auth_code", null).toString()
            if (userHasRefreshTokenStored()) {
                SpotifyConstants.REFRESHTOKEN =
                    pref.getString("spotify_refresh_token", null).toString()
                getApiTokenByRefreshToken()
            } else getApiTokenByAuthCode()
        } else if (!userHasAuthCodeStored()) startUserAuthentication()
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

    private fun startUserAuthentication() {
        val mainActivity = activity as MainActivity
        val nhf = mainActivity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        var nhfView = mainActivity.findViewById<FragmentContainerView>(R.id.nav_host_fragment)
        nhfView.visibility = VISIBLE

        nhf.navController.navigate(R.id.authFragment)
    }

    fun handleDataFromIntent(data: Uri) {

        pref = activity.applicationContext.getSharedPreferences("spotify_api_app",
            DaggerAppCompatActivity.MODE_PRIVATE
        )

        val mainPart: String = data.toString().split("?")[1]
        val arguments: List<String> = mainPart.split("&")
        val state = arguments[1].split("state=").toTypedArray()[1]

        if ((state == SpotifyConstants.STATE) && ("error" !in mainPart)) {
            val code = arguments[0].split("code=").toTypedArray()[1]
            SpotifyConstants.CODE = code

            val edit: SharedPreferences.Editor = pref.edit()
            edit.putString("spotify_auth_code", code)
            edit.apply()
        }
    }

    private fun userHasAuthCodeStored(): Boolean {
        return pref.getString("spotify_auth_code", null) != null
    }

    private fun userHasRefreshTokenStored(): Boolean {
        return pref.getString("spotify_refresh_token", null) != null
    }
}
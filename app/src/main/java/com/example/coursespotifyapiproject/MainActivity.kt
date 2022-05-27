package com.example.coursespotifyapiproject

import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_activity.*
import androidx.fragment.app.Fragment
import com.example.coursespotifyapiproject.ui.analytics.AnalyticsFragment
import com.example.coursespotifyapiproject.ui.auth.AuthFragment
import com.example.coursespotifyapiproject.ui.playlists.PlaylistsFragment
import com.example.coursespotifyapiproject.ui.user.UserFragment
import com.example.coursespotifyapiproject.data.api.ApiHelper
import com.example.coursespotifyapiproject.data.api.RetrofitBuilder
import com.example.coursespotifyapiproject.data.api2.ApiHelper2
import com.example.coursespotifyapiproject.data.api2.RetrofitBuilder2
import com.example.coursespotifyapiproject.data.model.AuthorizationResponse
import com.example.coursespotifyapiproject.data.model.User
import com.example.spotifysigninexample.SpotifyConstants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Base64.getEncoder
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var pref: SharedPreferences

    private val apiHelper = ApiHelper(RetrofitBuilder.apiService)
    private val apiHelper2 = ApiHelper2(RetrofitBuilder2.apiService)

    private lateinit var userFragment: UserFragment
    private lateinit var playlistsFragment: PlaylistsFragment
    private lateinit var analyticsFragment: AnalyticsFragment

    private lateinit var activeFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (applicationContext as App).appComponent.inject(this)

        if (intent.data != null) handleDataFromIntent(intent.data!!)

        setContentView(R.layout.main_activity)
        var bottomNav: BottomNavigationView = findViewById(R.id.navigation_bar)
        bottomNav.setOnNavigationItemSelectedListener(navListener)
        navigation_bar.visibility = View.GONE

        if (userHasAuthCodeStored()) {
            SpotifyConstants.CODE = pref.getString("spotify_auth_code", null).toString()
            if (userHasRefreshTokenStored())
            {
                SpotifyConstants.REFRESHTOKEN = pref.getString("spotify_refresh_token", null).toString()
                getApiTokenByRefreshToken()
            }
            else getApiTokenByAuthCode()
        }
        else if (!userHasAuthCodeStored()) startUserAuthentication(savedInstanceState)

    }



    private fun handleDataFromIntent(data: Uri) {
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

    private fun getApiTokenByAuthCode(){

        val authorizationParam = SpotifyConstants.CLIENT_ID + ":" + SpotifyConstants.CLIENT_SECRET
        var authorizationParamEncoded: String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                authorizationParamEncoded = getEncoder().encodeToString(authorizationParam.toByteArray())
            } else {
                TODO("VERSION.SDK_INT < O")
            }

        Log.d("fucking",SpotifyConstants.CODE)

        val retrofitData = apiHelper2.getApiTokenByAuthCode(
            "Basic $authorizationParamEncoded",
            "authorization_code",
            SpotifyConstants.CODE,
            SpotifyConstants.REDIRECT_URI
        )

        var token: String? = ""
        var refreshToken: String? = ""

        retrofitData.enqueue(object : Callback<AuthorizationResponse?> {
            override fun onResponse(
                call: Call<AuthorizationResponse?>,
                response: Response<AuthorizationResponse?>
            ) {
                token = response.body()?.accessToken ?: ""
                refreshToken = response.body()?.refreshToken ?: ""

                if (token != "" && refreshToken != "")
                {
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
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getApiTokenByRefreshToken()
    {
        val authorizationParam = SpotifyConstants.CLIENT_ID + ":" + SpotifyConstants.CLIENT_SECRET
        var authorizationParamEncoded: String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            authorizationParamEncoded = getEncoder().encodeToString(authorizationParam.toByteArray())
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        Log.d("fucking",SpotifyConstants.CODE)

        val retrofitData = apiHelper2.getApiTokenByRefreshToken(
            "Basic $authorizationParamEncoded",
            "refresh_token",
            SpotifyConstants.REFRESHTOKEN,
        )

        var token: String? = ""

        retrofitData.enqueue(object : Callback<AuthorizationResponse?> {
            override fun onResponse(
                call: Call<AuthorizationResponse?>,
                response: Response<AuthorizationResponse?>
            ) {
                token = response.body()?.accessToken ?: ""

                if (token != "")
                {
                    SpotifyConstants.TOKEN = token as String

                    val edit: SharedPreferences.Editor = pref.edit()
                    edit.putString("spotify_api_token", token)
                    edit.apply()
                    userAuthenticated()
                }

            }

            override fun onFailure(call: Call<AuthorizationResponse?>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun startUserAuthentication(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AuthFragment())
                .commitNow()
        }
    }

    private fun userAuthenticated() {
        userFragment = UserFragment()
        playlistsFragment = PlaylistsFragment()
        analyticsFragment = AnalyticsFragment()

        navigation_bar.visibility = View.VISIBLE

        supportFragmentManager.beginTransaction().add(R.id.container, analyticsFragment, "3")
            .hide(analyticsFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.container, playlistsFragment, "2")
            .hide(playlistsFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.container, userFragment, "1").commit()

        activeFragment = userFragment
    }

    private fun userHasAuthCodeStored(): Boolean {
        return pref.getString("spotify_auth_code", null) != null
    }

    private fun userHasRefreshTokenStored(): Boolean {
        return pref.getString("spotify_refresh_token", null) != null
    }

    private val navListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.navigation_user -> selectedFragment = userFragment
                R.id.navigation_list -> selectedFragment = playlistsFragment
                R.id.navigation_analytics -> selectedFragment = analyticsFragment
            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(selectedFragment).commit()
                activeFragment = selectedFragment
            }
            true
        }

}


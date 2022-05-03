package com.example.coursespotifyapiproject

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_activity.*
import androidx.fragment.app.Fragment
import com.example.coursespotifyapiproject.ui.analytics.AnalyticsFragment
import com.example.coursespotifyapiproject.ui.auth.AuthFragment
import com.example.coursespotifyapiproject.ui.playlists.PlaylistsFragment
import com.example.coursespotifyapiproject.ui.user.UserFragment
import com.example.spotifysigninexample.SpotifyConstants

class MainActivity : AppCompatActivity() {

    private lateinit var pref: SharedPreferences;
    private lateinit var userFragment: UserFragment
    private lateinit var playlistsFragment: PlaylistsFragment
    private lateinit var analyticsFragment: AnalyticsFragment


    private val authenticatedFromAuthFragment: () -> Unit = { ->

        val edit: SharedPreferences.Editor = pref.edit();
        edit.putString("spotify_auth_token", SpotifyConstants.TOKEN)
        edit.clear().apply()
        userAuthenticated()
    }

    private var fragment1 = AuthFragment(authenticatedFromAuthFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        var bottomNav: BottomNavigationView = findViewById(R.id.navigation_bar)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        navigation_bar.visibility = View.GONE

        pref = getSharedPreferences("spotify_api_app", MODE_PRIVATE);

        if (userHasAuthKeyStored()) {
            SpotifyConstants.TOKEN = pref.getString("spotify_auth_token", null)!!
            userAuthenticated()
        } else startUserAuthentication(savedInstanceState)

    }

    private fun startUserAuthentication(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment1)
                .commitNow()
        }
    }

    private fun userAuthenticated() {
        userFragment = UserFragment()
        playlistsFragment = PlaylistsFragment()
        analyticsFragment = AnalyticsFragment()

        navigation_bar.visibility = View.VISIBLE

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, userFragment)
            .commitNow()

    }

    private fun userHasAuthKeyStored(): Boolean {

        return pref.getString("spotify_auth_token", null) != null;
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
                supportFragmentManager.beginTransaction().replace(
                    R.id.container,
                    selectedFragment
                ).commit()
            }
            true
        }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fragment1.onActivityResult(requestCode, resultCode, data)

    }


}


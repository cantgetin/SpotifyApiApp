package com.example.coursespotifyapiproject

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
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var pref: SharedPreferences;

    private lateinit var userFragment: UserFragment
    private lateinit var playlistsFragment: PlaylistsFragment
    private lateinit var analyticsFragment: AnalyticsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (applicationContext as App).appComponent.inject(this)

        var data = getIntent().getData();
        if (data != null)
        {
            val mainPart: String = data.toString().split("#")[1]
            val arguments: List<String> = mainPart.split("&")
            val argument = arguments[0]
            val token = argument.split("=").toTypedArray()[1]
            val edit: SharedPreferences.Editor = pref.edit();
            edit.putString("spotify_auth_token", token)
            edit.clear().apply()
        }

        setContentView(R.layout.main_activity)

        var bottomNav: BottomNavigationView = findViewById(R.id.navigation_bar)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        navigation_bar.visibility = View.GONE



        if (userHasAuthKeyStored()) {
            SpotifyConstants.TOKEN = pref.getString("spotify_auth_token", null)!!
            userAuthenticated()
        } else startUserAuthentication(savedInstanceState)

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

}


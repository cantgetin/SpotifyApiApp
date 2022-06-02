package com.example.coursespotifyapiproject

import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_activity.*
import androidx.fragment.app.Fragment
import com.example.coursespotifyapiproject.ui.analytics.AnalyticsFragment
import com.example.coursespotifyapiproject.ui.playlists.PlaylistsFragment
import com.example.coursespotifyapiproject.ui.user.UserFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var pref: SharedPreferences

    private lateinit var authClient: AuthenticationClient

    private lateinit var userFragment: UserFragment
    private lateinit var playlistsFragment: PlaylistsFragment
    private lateinit var analyticsFragment: AnalyticsFragment

    private lateinit var activeFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (applicationContext as App).appComponent.inject(this)
        supportActionBar!!.elevation = 0f

        if (intent.data != null) handleDataFromIntent(intent.data!!)

        setContentView(R.layout.main_activity)
        var bottomNav: BottomNavigationView = findViewById(R.id.navigation_bar)
        bottomNav.setOnNavigationItemSelectedListener(navListener)
        navigation_bar.visibility = View.GONE

        authClient = AuthenticationClient(userAuthenticated)
        authClient.connect(savedInstanceState, this)
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

    private val userAuthenticated: () -> Unit = { ->
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
        supportActionBar!!.title = userFragment.toString()

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


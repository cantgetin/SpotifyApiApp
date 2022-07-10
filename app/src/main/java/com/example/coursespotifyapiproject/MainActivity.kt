package com.example.coursespotifyapiproject

import android.os.Bundle
import android.view.View.VISIBLE
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.coursespotifyapiproject.di.utils.FragmentFactory
import com.example.coursespotifyapiproject.ui.auth.AuthFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    private lateinit var authClient: AuthenticationClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authClient = AuthenticationClient(userAuthenticated, this)
        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(R.layout.main_activity)
        if (intent.data != null) authClient.handleDataFromIntent(intent.data!!)
        authClient.authenticate()

        val bottomNav: BottomNavigationView = findViewById(R.id.navigation_bar)

        var navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        var navController = navHostFragment.navController

        bottomNav.setupWithNavController(navController)
    }

    private val userAuthenticated: () -> Unit = {

        var navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        var navController = navHostFragment.navController
        navController.navigate(AuthFragmentDirections.actionSignInFragmentToUsersFragment())

        var bnv = findViewById<BottomNavigationView>(R.id.navigation_bar)
        bnv.visibility = VISIBLE

        var nhfView = findViewById<FragmentContainerView>(R.id.nav_host_fragment)
        nhfView.visibility = VISIBLE
    }


}
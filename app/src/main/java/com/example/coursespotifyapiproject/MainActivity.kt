package com.example.coursespotifyapiproject

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.coursespotifyapiproject.di.utils.FragmentFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import androidx.navigation.ui.NavigationUI

import android.view.MenuItem

import androidx.annotation.NonNull
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected

import com.google.android.material.navigation.NavigationBarView




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

        val nhf = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        nhf.navController.navigate(R.id.navigation_user)
    }


}
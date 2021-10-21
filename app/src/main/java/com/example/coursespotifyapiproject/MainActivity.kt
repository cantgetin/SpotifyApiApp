package com.example.coursespotifyapiproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_activity.*
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    val itemClickListener: () -> Unit = { ->

        var fragment2 = MainFragment()

        navigation_bar.visibility = View.VISIBLE

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment2)
            .commitNow()

    }

    var fragment1 = AuthFragment(itemClickListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        var bottomNav: BottomNavigationView = findViewById(R.id.navigation_bar)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        navigation_bar.visibility = View.GONE

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment1)
                .commitNow()
        }
    }

    private val navListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.navigation_user -> selectedFragment = MainFragment()
                R.id.navigation_list -> selectedFragment = ListFragment()
                R.id.navigation_analytics -> selectedFragment = AnalyticsFragment()
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


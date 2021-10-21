package com.example.coursespotifyapiproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    val itemClickListener: () -> Unit = { ->

        var fragment2 = MainFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment2)
            .commitNow()

    }

    var fragment1 = AuthFragment(itemClickListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment1)
                .commitNow()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fragment1.onActivityResult(requestCode, resultCode, data)
    }

}


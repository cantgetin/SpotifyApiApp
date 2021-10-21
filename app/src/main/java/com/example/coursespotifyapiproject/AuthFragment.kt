package com.example.coursespotifyapiproject

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.spotifysigninexample.SpotifyConstants
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import kotlinx.android.synthetic.main.auth_fragment.view.*


class AuthFragment(val itemClickListener: () -> Unit) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.auth_fragment, container, false)

        view.spotify_login_btn.setOnClickListener {
            val request = getAuthenticationRequest(AuthenticationResponse.Type.TOKEN)
            AuthenticationClient.openLoginActivity(
                getActivity(),
                SpotifyConstants.AUTH_TOKEN_REQUEST_CODE,
                request
            )

        }

        return view
    }

    private fun getAuthenticationRequest(type: AuthenticationResponse.Type): AuthenticationRequest {
        return AuthenticationRequest.Builder(
            SpotifyConstants.CLIENT_ID,
            type,
            SpotifyConstants.REDIRECT_URI
        )
            .setShowDialog(false)
            .setScopes(SpotifyConstants.SCOPES)
            .build()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (SpotifyConstants.AUTH_TOKEN_REQUEST_CODE == requestCode) {
            val response = AuthenticationClient.getResponse(resultCode, data)
            SpotifyConstants.TOKEN = response.accessToken
        }
        itemClickListener.invoke()
    }
}




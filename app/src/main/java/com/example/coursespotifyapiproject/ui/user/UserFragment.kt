package com.example.coursespotifyapiproject.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coursespotifyapiproject.R
import com.example.coursespotifyapiproject.data.model.Artist
import com.example.coursespotifyapiproject.ui.playlists.PlaylistDetailsFragment
import com.example.coursespotifyapiproject.utils.Status
import kotlinx.android.synthetic.main.user_fragment.*


class UserFragment() : Fragment() {

    private lateinit var viewModel: UserViewModel
    private lateinit var artistsAdapter: ArtistsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        var view =  inflater.inflate(R.layout.user_fragment, container, false)

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)


        recyclerView = view.findViewById(R.id.topArtistRecyclerView)
        artistsAdapter = ArtistsAdapter(arrayListOf())
        recyclerView.adapter = artistsAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)


        setupUI()

        return view
    }

    val itemClickListener: () -> Unit = {}

    private fun setupUI() {

        viewModel.getUserTopArtists().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    resource.data?.let { artist ->
                        artistsAdapter.addArtists(artist.items.take(5))
                        artistsAdapter.notifyDataSetChanged()
                        //progressBar.visibility = View.GONE
                    }
                }
                Status.ERROR -> {

                    Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
            }

        }

        viewModel.getUserInfo().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    resource.data?.let { user ->

                        userName.text = ("Hey, " + user.nickname)
                        userCountry.text = ("Country: " + user.country)
                        userProduct.text = ("Product: " + user.product)
                        userId.text = ("User id: " + user.id)
                        view?.let { Glide.with(it).load(user.images[0].url).into(userAvatar) };

                        progressBar.visibility = View.GONE

                        userName.visibility = View.VISIBLE
                        userCountry.visibility = View.VISIBLE
                        userProduct.visibility = View.VISIBLE
                        userId.visibility = View.VISIBLE
                        userAvatar.visibility = View.VISIBLE
                        topArtistsText.visibility = View.VISIBLE
                    }
                }
                Status.ERROR -> {

                    Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
            }
        }



    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) (activity as AppCompatActivity?)!!.supportActionBar?.title = this.toString()
    }

    override fun toString(): String {
        return "User Info"
    }

}




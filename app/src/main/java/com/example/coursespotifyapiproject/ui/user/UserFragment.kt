package com.example.coursespotifyapiproject.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coursespotifyapiproject.R
import com.example.coursespotifyapiproject.di.utils.ViewModelFactory
import com.example.coursespotifyapiproject.utils.Status
import kotlinx.android.synthetic.main.user_fragment.*
import javax.inject.Inject


class UserFragment @Inject constructor(
    viewModelFactory: ViewModelFactory
) : Fragment() {

    private val viewModel: UserViewModel by viewModels { viewModelFactory}
    private lateinit var artistsAdapter: ArtistsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view =  inflater.inflate(R.layout.user_fragment, container, false)

        recyclerView = view.findViewById(R.id.topArtistRecyclerView)
        artistsAdapter = ArtistsAdapter(arrayListOf())
        recyclerView.adapter = artistsAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        setupUI()

        return view
    }

    private fun setupUI() {

        viewModel.userTopArtists.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    resource.data?.let { artist ->
                        artistsAdapter.addArtists(artist.items.take(15))
                        artistsAdapter.notifyDataSetChanged()
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

        viewModel.userInfo.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    resource.data?.let { user ->

                        userName.text = ("Hey, " + user.nickname)
                        userCountry.text = ("Country: " + user.country)
                        userProduct.text = ("Product: " + user.product)
                        userId.text = ("User id: " + user.id)
                        view?.let { Glide.with(it).load(user.images[0].url).into(userAvatar) }

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




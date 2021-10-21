package com.example.coursespotifyapiproject.ui.playlists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursespotifyapiproject.R
import com.example.coursespotifyapiproject.utils.Status
import kotlinx.android.synthetic.main.playlists_fragment.*







class PlaylistsFragment() : Fragment() {

    private lateinit var adapter: PlaylistsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: PlaylistsViewModel
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        var view = inflater.inflate(R.layout.playlists_fragment, container, false)

        view.apply {
            recyclerView = view.findViewById(R.id.rView)
            progressBar = view.findViewById(R.id.progressBar)
            recyclerView.layoutManager = LinearLayoutManager(activity)
        }

        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlaylistsViewModel::class.java)

        setupUI()
        setupObservers()
    }


    val itemClickListener: (View, String) -> Unit = { view, id -> }

    fun setupUI()
    {
        adapter = PlaylistsAdapter(arrayListOf(),itemClickListener)
        /*recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )*/
        recyclerView.adapter = adapter

        var lol = 5
    }

    private fun setupObservers() {
        viewModel.getPlaylists().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    recyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    resource.data?.let { response ->
                        adapter.addPlaylists(response.items)
                        /*viewModel.setAllUsersToDatabase(
                            users = users.map { user ->
                                UserDB(
                                    id = user.id,
                                    name = user.name,
                                    avatar = user.avatar,
                                    email = user.email,
                                )
                            }
                        )*/
                    }
                }
                Status.ERROR -> {
                    recyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
            }
        }

        /*viewModel.localUsers.observe(viewLifecycleOwner) {
            it
        }*/
    }



}
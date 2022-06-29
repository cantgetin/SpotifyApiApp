package com.example.coursespotifyapiproject.ui.playlists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursespotifyapiproject.utils.Status
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.coursespotifyapiproject.R
import com.example.coursespotifyapiproject.di.utils.ViewModelFactory
import com.example.coursespotifyapiproject.ui.tracks.TracksFragmentDirections
import javax.inject.Inject


class PlaylistsFragment @Inject constructor(
    viewModelFactory: ViewModelFactory
) : Fragment() {

    private lateinit var adapter: PlaylistsAdapter
    private lateinit var recyclerView: RecyclerView
    private val viewModel: PlaylistsViewModel by viewModels { viewModelFactory }
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.playlists_fragment, container, false)

        view.apply {
            recyclerView = view.findViewById(R.id.rView)
            progressBar = view.findViewById(R.id.progressBar)
            recyclerView.layoutManager = LinearLayoutManager(activity)
        }

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObservers()
    }


    private val itemClickListener: (View, String, String) -> Unit = { v, id, name ->


        val action = PlaylistsFragmentDirections.toPlaylistTracks(id,name)
        v.findNavController().navigate(action)

    }


    private fun setupUI() {
        adapter = PlaylistsAdapter(arrayListOf(), itemClickListener)
        recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getPlaylists().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    recyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    resource.data?.let { response ->
                        adapter.addPlaylists(response.items)
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
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) (activity as AppCompatActivity?)!!.supportActionBar?.title = this.toString()
    }

    override fun toString(): String {
        return "Playlists"
    }
}
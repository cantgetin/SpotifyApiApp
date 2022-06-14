package com.example.coursespotifyapiproject.ui.tracks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursespotifyapiproject.R
import com.example.coursespotifyapiproject.data.model.Track
import com.example.coursespotifyapiproject.ui.track.TrackDetailsFragment
import com.example.coursespotifyapiproject.utils.Status


class TracksFragment(private var playlistId: String, private val playlistName: String, private val likedTracks: Boolean) : Fragment() {

    private lateinit var adapter: TracksAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: TracksViewModel
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.tracks_fragment, container, false)

        view.apply {
            recyclerView = view.findViewById(R.id.rView)
            progressBar = view.findViewById(R.id.progressBar)
            recyclerView.layoutManager = LinearLayoutManager(activity)
        }

        if (!likedTracks) {
            val lol: String = (activity as AppCompatActivity?)!!.supportActionBar?.title as String
            (activity as AppCompatActivity?)!!.supportActionBar?.title = lol.plus(" > ").plus(playlistName)
        }

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[TracksViewModel::class.java]

        setupUI()
        setupObservers()
    }


    val itemClickListener: (Track) -> Unit = { track ->
        requireActivity().supportFragmentManager.beginTransaction().hide(this)
            .add(R.id.container, TrackDetailsFragment(track)).addToBackStack("tracks_to_track").commit()

    }

    private fun setupUI() {
        adapter = TracksAdapter(arrayListOf(), itemClickListener)
        recyclerView.adapter = adapter
    }

    private fun setupObservers() {

        if (likedTracks) {
            viewModel.getLikedTracks().observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let { response ->
                            adapter.addTracks(response)
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
        else {
            viewModel.getTracks(playlistId).observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let { response ->
                            adapter.addTracks(response)
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
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (likedTracks) {
            if (!hidden) (activity as AppCompatActivity?)!!.supportActionBar?.title = "Liked tracks"
        }
    }
}
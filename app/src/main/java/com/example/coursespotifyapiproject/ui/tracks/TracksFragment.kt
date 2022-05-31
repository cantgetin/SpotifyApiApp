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


class TracksFragment(private var playlistId: String, private val playlistName: String) : Fragment() {

    private lateinit var adapter: TracksAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: TracksViewModel
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        var view = inflater.inflate(R.layout.tracks_fragment, container, false)

        view.apply {
            recyclerView = view.findViewById(R.id.rView)
            progressBar = view.findViewById(R.id.progressBar)
            recyclerView.layoutManager = LinearLayoutManager(activity)
        }

        var lol: String = (activity as AppCompatActivity?)!!.supportActionBar?.title as String
        (activity as AppCompatActivity?)!!.supportActionBar?.title = lol.plus(" > ").plus(playlistName)

        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TracksViewModel::class.java)

        setupUI()
        setupObservers()
    }


    val itemClickListener: (Track) -> Unit = { track ->
        requireActivity().supportFragmentManager.beginTransaction().hide(this)
            .add(R.id.container, TrackDetailsFragment(track)).addToBackStack("tracks_to_track").commit()

    }

    fun setupUI() {
        adapter = TracksAdapter(arrayListOf(), itemClickListener)
        recyclerView.adapter = adapter
    }

    private fun setupObservers() {
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
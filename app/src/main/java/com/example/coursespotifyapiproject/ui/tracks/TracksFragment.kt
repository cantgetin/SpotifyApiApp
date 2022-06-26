package com.example.coursespotifyapiproject.ui.tracks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursespotifyapiproject.R
import com.example.coursespotifyapiproject.data.model.Track
import com.example.coursespotifyapiproject.di.utils.ViewModelFactory
import com.example.coursespotifyapiproject.utils.Status
import javax.inject.Inject


class TracksFragment @Inject constructor(
    viewModelFactory: ViewModelFactory
) : Fragment() {

    private lateinit var adapter: TracksAdapter
    private lateinit var recyclerView: RecyclerView
    private val viewModel: TracksViewModel by viewModels { viewModelFactory}
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

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: TracksFragmentArgs by navArgs()
        val id = args.playlistId
        val name = args.playlistName
        val isLiked = args.likedTracks

//        val lol: String = (activity as AppCompatActivity?)!!.supportActionBar?.title as String
//        (activity as AppCompatActivity?)!!.supportActionBar?.title = lol.plus(" > ").plus(name)

        setupUI()
        setupObservers(id, isLiked)
    }


    private val itemClickListener: (View, Track) -> Unit = { v, track ->
        val action = TracksFragmentDirections.toTrackDetails(track)
        v.findNavController().navigate(action)
    }

    private fun setupUI() {
        adapter = TracksAdapter(arrayListOf(), itemClickListener)
        recyclerView.adapter = adapter
    }

    private fun setupObservers(playlistId: String, likedTracks: Boolean) {
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
}
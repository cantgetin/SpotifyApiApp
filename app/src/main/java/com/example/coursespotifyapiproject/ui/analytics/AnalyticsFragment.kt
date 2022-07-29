package com.example.coursespotifyapiproject.ui.analytics

import com.example.coursespotifyapiproject.di.utils.ViewModelFactory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursespotifyapiproject.R
import com.example.coursespotifyapiproject.data.model.Genre
import com.example.coursespotifyapiproject.ui.playlists.PlaylistsFragmentDirections
import com.example.coursespotifyapiproject.utils.Status

import javax.inject.Inject

class AnalyticsFragment @Inject constructor(
    viewModelFactory: ViewModelFactory
) : Fragment() {


    private lateinit var adapter: AnalyticsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private val viewModel: AnalyticsViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.analytics_fragment, container, false)

        view.apply {
            recyclerView = view.findViewById(R.id.analyticsView)
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


    private val itemClickListener: (View, Genre) -> Unit = { v,genre ->

        val action = AnalyticsFragmentDirections.toGenreDetails(genre)
        v.findNavController().navigate(action)
    }

    private fun setupUI() {
        adapter = AnalyticsAdapter(arrayListOf(), itemClickListener)
        recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.analytics.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    recyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    resource.data?.let { response ->

                        adapter.addGenres(response)
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
        return "Analytics"
    }
}
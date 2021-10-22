package com.example.coursespotifyapiproject.ui.analytics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursespotifyapiproject.R
import com.example.coursespotifyapiproject.utils.Status

class AnalyticsFragment() : Fragment() {


    private lateinit var adapter: AnalyticsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AnalyticsViewModel
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        var view = inflater.inflate(R.layout.analytics_fragment, container, false)

        view.apply {
            recyclerView = view.findViewById(R.id.analyticsView)
            progressBar = view.findViewById(R.id.progressBar)
            recyclerView.layoutManager = LinearLayoutManager(activity)
        }

        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AnalyticsViewModel::class.java)

        setupUI()
        setupObservers()
    }


    val itemClickListener: (String) -> Unit = { id ->
        /*requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, PlaylistDetailsFragment(id)).commitNow()*/
    }

    fun setupUI() {
        adapter = AnalyticsAdapter(arrayListOf(), itemClickListener)
        recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getPlaylists().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    recyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    resource.data?.let { response ->

                        var jopa: ArrayList<String> = ArrayList<String>()

                        response.items.forEach{
                            it.genres.forEach{
                                jopa.add(it)
                            }
                        }

                        adapter.addGenres(jopa)
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
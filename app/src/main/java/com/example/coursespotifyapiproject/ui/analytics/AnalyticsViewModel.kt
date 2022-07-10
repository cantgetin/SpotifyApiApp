package com.example.coursespotifyapiproject.ui.analytics

import androidx.lifecycle.ViewModel
import com.example.coursespotifyapiproject.data.db.Repository
import javax.inject.Inject

class AnalyticsViewModel @Inject constructor(repository: Repository) : ViewModel() {

    val analytics = repository.userAnalytics

}


package com.jgbravo.civitatistechnical.ui.main

import androidx.lifecycle.ViewModel
import com.jgbravo.civitatistechnical.data.dtos.entity.Job
import com.jgbravo.civitatistechnical.data.manager.JobsManager
import com.jgbravo.civitatistechnical.data.remote.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val jobsManager: JobsManager
) : ViewModel() {

    sealed class MainState {
        object NotStarted : MainState()
        object Loading : MainState()
        class Success(val jobList: List<Job>) : MainState()
        class Error(val errorMsg: String) : MainState()
    }

    private val _jobState = MutableStateFlow<MainState>(MainState.NotStarted)
    val jobState: StateFlow<MainState> get() = _jobState

    private val jobList = ArrayList<Job>()

    suspend fun getJobs() {
        jobsManager.getJobsFilteredByDaysAgo(5).collect { resource ->
            when (resource) {
                is Resource.Loading -> {
                    if (jobList.isEmpty()) {
                        _jobState.value = MainState.Loading
                    }
                }
                is Resource.Error -> {
                    _jobState.value = MainState.Error(resource.message.toString())
                }
                is Resource.Success -> {
                    jobList.add(resource.data as Job)
                    _jobState.value = MainState.Success(
                        jobList
                    )
                }
            }
        }
    }
}
package com.jgbravo.civitatistechnical.ui.main.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jgbravo.civitatistechnical.data.dtos.entity.JobDetails
import com.jgbravo.civitatistechnical.data.manager.JobsManager
import com.jgbravo.civitatistechnical.data.remote.utils.Resource
import com.jgbravo.civitatistechnical.utils.getDateFromDaysAgo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class MainViewModel @Inject constructor(
    private val jobsManager: JobsManager
) : ViewModel() {

    sealed class MainState {
        object NotStarted : MainState()
        object Loading : MainState()
        class Success(val jobList: List<JobDetails>) : MainState()
        class Error(val errorMsg: String) : MainState()
    }

    private val _jobState = MutableStateFlow<MainState>(MainState.NotStarted)
    val jobState: StateFlow<MainState> get() = _jobState

    private val jobList = ArrayList<JobDetails>()

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
                    jobList.add(resource.data as JobDetails)
                    _jobState.value = MainState.Success(
                        jobList
                    )
                }
            }
        }
    }
}
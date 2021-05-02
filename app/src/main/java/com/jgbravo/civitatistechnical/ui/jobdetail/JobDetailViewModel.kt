package com.jgbravo.civitatistechnical.ui.jobdetail

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
class JobDetailViewModel @Inject constructor(
    private val jobsManager: JobsManager
) : ViewModel() {

    sealed class JobDetailState {
        object NotStarted : JobDetailState()
        object Loading : JobDetailState()
        class Success(val job: Job) : JobDetailState()
        class Error(val errorMsg: String) : JobDetailState()
    }

    private val _jobDetailState =
        MutableStateFlow<JobDetailState>(JobDetailState.NotStarted)
    val jobDetailState: StateFlow<JobDetailState> get() = _jobDetailState


    suspend fun getJobByID(id: String) {
        jobsManager.getJobByID(id).collect { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _jobDetailState.value = JobDetailState.Loading

                }
                is Resource.Error -> {
                    _jobDetailState.value =
                        JobDetailState.Error(resource.message.toString())
                }
                is Resource.Success -> {
                    _jobDetailState.value =
                        JobDetailState.Success(resource.data as Job)
                }
            }
        }
    }
}
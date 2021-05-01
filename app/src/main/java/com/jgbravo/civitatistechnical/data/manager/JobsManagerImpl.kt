package com.jgbravo.civitatistechnical.data.manager

import com.jgbravo.civitatistechnical.data.datasource.JobDataSource
import com.jgbravo.civitatistechnical.data.dtos.entity.Job
import com.jgbravo.civitatistechnical.data.remote.utils.Resource
import com.jgbravo.civitatistechnical.utils.getDateFromDaysAgo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class JobsManagerImpl @Inject constructor(
    private val datasource: JobDataSource
) : JobsManager {

    override fun getJobsFilteredByDaysAgo(daysAgo: Int): Flow<Resource<Job>> = flow {
        emit(Resource.Loading)

        datasource.getAllJobs().collect { resourceList ->
            when (resourceList) {
                is Resource.Loading -> {
                    emit(Resource.Loading)
                }
                is Resource.Error -> {
                    emit(Resource.Error())
                }
                is Resource.Success -> {
                    (resourceList.data as List<Job>)
                        .sortedBy { it.createdAt }
                        .forEach { job ->
                            if (job.createdAt.after(getDateFromDaysAgo(daysAgo))) {
                                emit(Resource.Success<Job>(job))
                            }
                        }
                }
            }
        }
    }

    override fun getJobByID(id: String): Flow<Resource<Job>> = flow {
        emit(Resource.Loading)

        datasource.getAllJobs().collect { resourceList ->
            when (resourceList) {
                is Resource.Loading -> {
                    emit(Resource.Loading)
                }
                is Resource.Error -> {
                    emit(Resource.Error())
                }
                is Resource.Success -> {
                    val job = (resourceList.data as List<Job>).find { it.id == id }
                    emit(Resource.Success<Job>(job))
                }
            }
        }
    }
}
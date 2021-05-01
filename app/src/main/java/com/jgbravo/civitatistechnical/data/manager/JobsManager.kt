package com.jgbravo.civitatistechnical.data.manager

import com.jgbravo.civitatistechnical.data.dtos.entity.Job
import com.jgbravo.civitatistechnical.data.remote.utils.Resource
import kotlinx.coroutines.flow.Flow

interface JobsManager {

    fun getJobsFilteredByDaysAgo(daysAgo: Int): Flow<Resource<Job>>

    fun getJobByID(id: String): Flow<Resource<Job>>
}
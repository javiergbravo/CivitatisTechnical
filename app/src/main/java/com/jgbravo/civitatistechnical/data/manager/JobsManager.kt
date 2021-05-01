package com.jgbravo.civitatistechnical.data.manager

import com.jgbravo.civitatistechnical.data.dtos.entity.JobDetails
import com.jgbravo.civitatistechnical.data.remote.utils.Resource
import kotlinx.coroutines.flow.Flow
import java.util.*

interface JobsManager {

    fun getJobsFilteredByDaysAgo(daysAgo: Int): Flow<Resource<JobDetails>>
}
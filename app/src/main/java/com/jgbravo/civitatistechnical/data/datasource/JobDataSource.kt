package com.jgbravo.civitatistechnical.data.datasource

import com.jgbravo.civitatistechnical.data.dtos.entity.Job
import com.jgbravo.civitatistechnical.data.remote.utils.Resource
import kotlinx.coroutines.flow.Flow

interface JobDataSource {

    fun getAllJobs(): Flow<Resource<List<Job>>>
}
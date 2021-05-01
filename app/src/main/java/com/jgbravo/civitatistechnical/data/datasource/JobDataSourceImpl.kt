package com.jgbravo.civitatistechnical.data.datasource

import com.jgbravo.civitatistechnical.data.dtos.entity.JobDetails
import com.jgbravo.civitatistechnical.data.dtos.mapper.JobDetailsEntityMapper
import com.jgbravo.civitatistechnical.data.dtos.mapper.forLists
import com.jgbravo.civitatistechnical.data.remote.JobsApi
import com.jgbravo.civitatistechnical.data.remote.exceptions.IllegalMappingException
import com.jgbravo.civitatistechnical.data.remote.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class JobDataSourceImpl @Inject constructor(
    private val api: JobsApi
) : JobDataSource {

    override fun getAllJobs(): Flow<Resource<List<JobDetails>>> = flow {
        emit(Resource.Loading)
        try {
            val response = api.getAllJobs()
            if (response.isSuccessful && response.body() != null) {
                val jobList: List<JobDetails> =
                    JobDetailsEntityMapper().forLists().invoke(response.body()!!)
                emit(Resource.Success<List<JobDetails>>(jobList))
            } else {
                emit(Resource.Error())
            }
        } catch (errorMapping: IllegalMappingException) {
            errorMapping.printStackTrace()
            emit(Resource.Error(errorMapping, errorMapping.message))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e, e.message))
        }
    }
}
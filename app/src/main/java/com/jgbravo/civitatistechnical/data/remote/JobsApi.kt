package com.jgbravo.civitatistechnical.data.remote

import com.jgbravo.civitatistechnical.data.dtos.indto.JobInDTO
import com.jgbravo.civitatistechnical.data.remote.utils.Endpoints
import retrofit2.Response
import retrofit2.http.GET

interface JobsApi {

    @GET(Endpoints.POSITIONS)
    suspend fun getAllJobs(): Response<List<JobInDTO>>
}
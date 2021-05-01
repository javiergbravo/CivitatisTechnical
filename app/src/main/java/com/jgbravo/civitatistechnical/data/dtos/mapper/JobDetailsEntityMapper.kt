package com.jgbravo.civitatistechnical.data.dtos.mapper

import com.jgbravo.civitatistechnical.data.dtos.entity.JobDetails
import com.jgbravo.civitatistechnical.data.dtos.indto.JobInDTO
import com.jgbravo.civitatistechnical.data.remote.exceptions.IllegalMappingException

class JobDetailsEntityMapper : Mapper<JobInDTO, JobDetails> {

    override suspend fun map(model: JobInDTO): JobDetails = JobDetails(
        id = model.id ?: throw IllegalMappingException("id"),
        type = model.type,
        url = model.url,
        createdAt = model.createdAt  ?: throw IllegalMappingException("createAt"),
        company = model.company ?: throw IllegalMappingException("company"),
        companyUrl = model.companyUrl ?: throw IllegalMappingException("companyUrl"),
        location = model.location,
        title = model.title  ?: throw IllegalMappingException("title"),
        description = model.description,
        howToApply = model.howToApply,
        companyLogo = model.companyLogo
    )
}
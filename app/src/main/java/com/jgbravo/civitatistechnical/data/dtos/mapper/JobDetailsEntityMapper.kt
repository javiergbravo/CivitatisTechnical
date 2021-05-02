package com.jgbravo.civitatistechnical.data.dtos.mapper

import com.jgbravo.civitatistechnical.data.dtos.entity.Job
import com.jgbravo.civitatistechnical.data.dtos.indto.JobInDTO
import com.jgbravo.civitatistechnical.data.remote.exceptions.IllegalMappingException

class JobDetailsEntityMapper : Mapper<JobInDTO, Job> {

    override suspend fun map(model: JobInDTO): Job = Job(
        id = model.id ?: throw IllegalMappingException("id"),
        type = model.type,
        url = model.url,
        createdAt = model.createdAt,
        company = model.company ?: throw IllegalMappingException("company"),
        companyUrl = model.companyUrl,
        location = model.location,
        title = model.title ?: throw IllegalMappingException("title"),
        description = model.description ?: throw IllegalMappingException("description"),
        companyLogo = model.companyLogo
    )
}
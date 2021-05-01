package com.jgbravo.civitatistechnical.data.dtos.entity

import java.util.*

data class Job(
    val id: String,
    val type: String?,
    val url: String?,
    val createdAt: Date,
    val company: String,
    val companyUrl: String?,
    val location: String?,
    val title: String,
    val description: String,
    val howToApply: String?,
    val companyLogo: String?
)

package com.jgbravo.civitatistechnical.data.dtos.indto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class JobInDTO(
    @Json(name = "id")
    val id: String?,
    @Json(name = "type")
    val type: String?,
    @Json(name = "url")
    val url: String?,
    @Json(name = "created_at")
    val createdAt: Date?,
    @Json(name = "company")
    val company: String?,
    @Json(name = "company_url")
    val companyUrl: String?,
    @Json(name = "location")
    val location: String?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "how_to_apply")
    val howToApply: String?,
    @Json(name = "company_logo")
    val companyLogo: String?
)
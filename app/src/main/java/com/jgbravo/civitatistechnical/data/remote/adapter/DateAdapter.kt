package com.jgbravo.civitatistechnical.data.remote.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter {

    companion object {
        const val FULL_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    }

    private val df = SimpleDateFormat(FULL_PATTERN, Locale.getDefault())

    @ToJson
    fun toJson(value: Date?): String? {
        return value?.let {
            df.format(it)
        }
    }

    @FromJson
    fun fromJson(source: String): Date? {
        val date = df.parse(source)
        date?.let {
            it.time += df.timeZone.rawOffset
        }
        return date
    }
}
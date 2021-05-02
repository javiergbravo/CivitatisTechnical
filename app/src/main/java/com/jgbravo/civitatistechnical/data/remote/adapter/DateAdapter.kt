package com.jgbravo.civitatistechnical.data.remote.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter {

    companion object {
        const val FULL_PATTERN = "EEE MMM d HH:mm:ss z yyyy"
    }

    private val df = SimpleDateFormat(FULL_PATTERN, Locale.getDefault())

    @ToJson
    fun toJson(value: Date?): String? {
        return value?.let {
            df.format(it)
        }
    }

    @FromJson
    fun fromJson(source: String?): Date? = try {
        if (source == null) {
            null
        } else {
            df.parse(source)
        }
    } catch (e: Exception) {
        null
    }

}
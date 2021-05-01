package com.jgbravo.civitatistechnical.utils

import java.text.SimpleDateFormat
import java.util.*

fun getDateFromDaysAgo(daysAgo: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)

    return calendar.time
}

fun convertDateToShortString(date: Date): String {
    val pattern = "EEE dd MMM yyyy"
    val simpleDateFormat = SimpleDateFormat(pattern, Locale("es", "ES"))
    return try {
        simpleDateFormat.format(date) ?: date.toString()
    } catch (e: Exception) {
        date.toString()
    }

}
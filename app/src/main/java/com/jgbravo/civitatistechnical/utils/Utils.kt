package com.jgbravo.civitatistechnical.utils

import android.os.Build
import android.text.Html
import android.text.Spanned
import java.text.SimpleDateFormat
import java.util.*

fun getDateFromDaysAgo(daysAgo: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)

    return calendar.time
}

fun convertDateToShortString(date: Date): String {
    val simpleDateFormat = SimpleDateFormat(DateConstants.SHORT_PATTERN, Locale.getDefault())
    return try {
        simpleDateFormat.format(date) ?: date.toString()
    } catch (e: Exception) {
        date.toString()
    }
}

fun String.convertFromHTML(): Spanned? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
} else {
    Html.fromHtml(this)
}

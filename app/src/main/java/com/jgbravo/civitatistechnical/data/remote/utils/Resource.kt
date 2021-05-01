package com.jgbravo.civitatistechnical.data.remote.utils

import com.jgbravo.civitatistechnical.R

sealed class Resource<out T : Any> {

    object Loading : Resource<Nothing>()

    data class Success<out T : Any>(val data: Any?) : Resource<T>()

    data class Error(
        val error: Exception? = null,
        val message: String? = "Error desconocido"
    ) : Resource<Nothing>()
}

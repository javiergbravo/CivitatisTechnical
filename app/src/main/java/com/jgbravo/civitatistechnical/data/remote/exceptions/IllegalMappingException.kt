package com.jgbravo.civitatistechnical.data.remote.exceptions

class IllegalMappingException(private val property: String) : Exception() {

    override val message = "Error al mappear la property $property"
}
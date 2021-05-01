package com.jgbravo.civitatistechnical.data.dtos.mapper

/**
 * Utils to map models to entities
 */

interface Mapper<M, E> {
    suspend fun map(from: M): E
}

fun <M, E> Mapper<M, E>.forLists(): suspend (List<M>) -> List<E> {
    return { list -> list.map { item -> map(item) } }
}
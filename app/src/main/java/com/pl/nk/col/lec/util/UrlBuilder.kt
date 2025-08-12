package com.pl.nk.col.lec.util

class UrlBuilder(private val baseUrl: String) {

    private val parameters = mutableMapOf<String, String>()

    fun addParameter(key: String, value: String?): UrlBuilder {
        value?.let { parameters[key] = it }
        return this
    }

    fun build(): String {
        val query = parameters.entries.joinToString("&") { "${it.key}=${it.value}" }
        return "$baseUrl$query"
    }
}
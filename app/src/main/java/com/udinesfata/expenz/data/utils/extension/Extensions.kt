package com.udinesfata.expenz.data.utils.extension

import java.time.Instant
import java.time.format.DateTimeFormatter

fun Instant.toIsoString(): String {
    return DateTimeFormatter.ISO_INSTANT.format(this)
}
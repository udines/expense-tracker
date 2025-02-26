package com.udinesfata.expenz.utils

import java.text.NumberFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatCurrencyIdr(amount: Double): String {
    val localeID = Locale("id", "ID")
    val formatter = NumberFormat.getCurrencyInstance(localeID)
    return formatter.format(amount).replace(",00", "")
}

fun formatDateReadable(date: Instant): String {
    val formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale.ENGLISH)
    return date.atZone(ZoneId.systemDefault()).format(formatter)
}
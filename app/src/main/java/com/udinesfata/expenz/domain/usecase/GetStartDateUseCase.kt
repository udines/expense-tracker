package com.udinesfata.expenz.domain.usecase

import java.time.Instant
import java.util.Calendar
import java.util.TimeZone

class GetStartDateUseCase {
    operator fun invoke(): Instant {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val result = calendar.toInstant()
        return result
    }
}
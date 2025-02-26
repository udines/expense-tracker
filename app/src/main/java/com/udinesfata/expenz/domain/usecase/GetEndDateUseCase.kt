package com.udinesfata.expenz.domain.usecase

import java.time.Instant
import java.util.Calendar
import java.util.Locale

class GetEndDateUseCase {
    operator fun invoke(startDate: Instant): Instant {
        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.timeInMillis = startDate.toEpochMilli()
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        return calendar.toInstant()
    }
}
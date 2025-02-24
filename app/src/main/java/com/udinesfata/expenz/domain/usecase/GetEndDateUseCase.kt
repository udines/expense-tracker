package com.udinesfata.expenz.domain.usecase

import java.time.Instant
import java.util.Calendar

class GetEndDateUseCase {
    operator fun invoke(startDate: Instant): Instant {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = startDate.toEpochMilli()
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        return calendar.toInstant()
    }
}
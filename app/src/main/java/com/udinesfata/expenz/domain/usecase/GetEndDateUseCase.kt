package com.udinesfata.expenz.domain.usecase

import java.util.Calendar
import java.util.Date

class GetEndDateUseCase {
    operator fun invoke(startDate: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = startDate
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        return calendar.time
    }
}
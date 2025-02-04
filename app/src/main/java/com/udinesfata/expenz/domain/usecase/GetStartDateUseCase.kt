package com.udinesfata.expenz.domain.usecase

import java.util.Calendar
import java.util.Date

class GetStartDateUseCase {
    operator fun invoke(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        return calendar.time
    }
}
package com.udinesfata.expenz.domain.usecase

import java.util.Calendar
import java.util.Date

class GetLastMonthStartDate {
    operator fun invoke(): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        return calendar.time
    }
}
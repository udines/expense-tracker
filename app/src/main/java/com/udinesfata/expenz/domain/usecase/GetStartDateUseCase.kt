package com.udinesfata.expenz.domain.usecase

import java.time.Instant
import java.util.Calendar

class GetStartDateUseCase {
    operator fun invoke(): Instant {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        return calendar.toInstant()
    }
}
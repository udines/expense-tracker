package com.udinesfata.expenz.domain.entity.params

import java.time.Instant

data class BudgetParams(
    val startDate: Instant? = null,
    val endDate: Instant? = null
)
package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.params.TransactionParams
import com.udinesfata.expenz.domain.repository.TransactionRepository
import java.util.Calendar

class GetLastMonthTransactionsUseCase(
    private val transactionRepositoryImpl: TransactionRepository
) {
    suspend operator fun invoke(): List<Transaction> {
        val startDate = Calendar.getInstance().apply { add(Calendar.MONTH, -1) }.time
        val endDate = Calendar.getInstance().apply { add(Calendar.SECOND, -1) }.time
        return transactionRepositoryImpl.getTransactions(
            TransactionParams(
                startDate = startDate.toString(),
                endDate = endDate.toString(),
            )
        )
    }

}
package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.data.utils.mapper.toEntity
import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.entity.request.TransactionRequest
import com.udinesfata.expenz.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.Instant

class CreateTransactionUseCase(
    private val transactionRepositoryImpl: TransactionRepository
) {
    operator fun invoke(
        walletId: Int,
        amount: Double,
        categoryId: Int,
        date: Instant,
        notes: String,
        type: String
    ): Flow<Transaction> {
        val request = TransactionRequest(walletId, amount, categoryId, date, notes, type)
        return flow {
            val localTransaction = transactionRepositoryImpl.createTransaction(request.toEntity(), true)
            emit(localTransaction)
            val remoteTransaction = transactionRepositoryImpl.createTransaction(request.toEntity(), false)
            emit(remoteTransaction)
        }
    }
}
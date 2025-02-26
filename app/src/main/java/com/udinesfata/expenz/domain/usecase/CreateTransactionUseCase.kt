package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.data.utils.mapper.toEntity
import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.entity.request.TransactionRequest
import com.udinesfata.expenz.domain.repository.TransactionRepository
import com.udinesfata.expenz.domain.repository.WalletRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.Instant

class CreateTransactionUseCase(
    private val transactionRepository: TransactionRepository,
    private val walletRepositoryImpl: WalletRepository,
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
            val localTransaction = transactionRepository.createTransaction(request.toEntity(), true)
            val localWallet = walletRepositoryImpl.getWallet(localTransaction.walletId, true)
            val newBalance = if (type == "income") {
                localWallet!!.balance + localTransaction.amount
            } else {
                localWallet!!.balance - localTransaction.amount
            }
            walletRepositoryImpl.updateWallet(localWallet.copy(balance = newBalance), true)
            emit(localTransaction)
        }
    }
}
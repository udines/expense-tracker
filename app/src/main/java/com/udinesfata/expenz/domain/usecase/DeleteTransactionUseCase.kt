package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.Transaction
import com.udinesfata.expenz.domain.entity.Wallet
import com.udinesfata.expenz.domain.repository.TransactionRepository
import com.udinesfata.expenz.domain.repository.WalletRepository

class DeleteTransactionUseCase(
    private val transactionRepository: TransactionRepository,
    private val walletRepository: WalletRepository
) {
    suspend operator fun invoke(id: Int): Boolean {
        val transaction = transactionRepository.getTransaction(id, true)
        transaction?.let {
            val wallet = walletRepository.getWallet(transaction.walletId, true)
            wallet?.let {
                deleteTransactionAndUpdateWallet(
                    id = id,
                    isExpense = transaction.isExpense(),
                    wallet = it,
                    transaction = transaction
                )
            }
        }

        return true
    }

    private suspend fun deleteTransactionAndUpdateWallet(
        id: Int,
        isExpense: Boolean,
        wallet: Wallet,
        transaction: Transaction
    ) {
        if (isExpense) {
            walletRepository.updateWallet(
                wallet.copy(balance = wallet.balance + transaction.amount),
                true
            )
        } else {
            walletRepository.updateWallet(
                wallet.copy(balance = wallet.balance - transaction.amount),
                true
            )
        }
        transactionRepository.deleteTransaction(id, true)
    }
}
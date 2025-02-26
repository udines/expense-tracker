package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.TransactionItem
import com.udinesfata.expenz.domain.entity.params.TransactionParams
import com.udinesfata.expenz.domain.repository.CategoryRepository
import com.udinesfata.expenz.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetThisMonthTransactionsUseCase(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
    private val getStartDateUseCase: GetStartDateUseCase,
    private val getEndDateUseCase: GetEndDateUseCase,
) {
    operator fun invoke(walletId: Int): Flow<List<TransactionItem>> {
        val startDate = getStartDateUseCase()
        val endDate = getEndDateUseCase(startDate)
        return flow {
            val result = mutableListOf<TransactionItem>()
            val transactions = transactionRepository.getTransactions(
                TransactionParams(
                    walletId = walletId,
                    startDate = startDate,
                    endDate = endDate,
                ),
                true
            )
            for (transaction in transactions) {
                val category = categoryRepository.getCategory(transaction.categoryId, true)
                result.add(TransactionItem(transaction, category))
            }
            emit(result)
        }
    }
}
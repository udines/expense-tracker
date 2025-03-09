package com.udinesfata.expenz.domain.usecase

import com.udinesfata.expenz.domain.entity.ui.TopSpending
import com.udinesfata.expenz.domain.entity.params.CategoryParams
import com.udinesfata.expenz.domain.entity.params.TransactionParams
import com.udinesfata.expenz.domain.repository.CategoryRepository
import com.udinesfata.expenz.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTopSpendingUseCase(
    private val categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository,
    private val getStartDateUseCase: GetStartDateUseCase,
    private val getEndDateUseCase: GetEndDateUseCase,
) {
    operator fun invoke(): Flow<List<TopSpending>> {
        val startDate = getStartDateUseCase()
        val endDate = getEndDateUseCase(startDate)
        return flow {
            val topSpending = mutableListOf<TopSpending>()
            val categories = categoryRepository.getCategories(CategoryParams(), true)
            for (category in categories) {
                val transactions = transactionRepository.getTransactions(
                    TransactionParams(
                        startDate = startDate,
                        endDate = endDate,
                        categoryId = category.id
                    ), true
                )
                topSpending.add(
                    TopSpending(
                        category = category,
                        amount = transactions.sumOf { it.amount }
                    )
                )
            }
            topSpending.sortByDescending { it.amount }
            emit(topSpending)
        }
    }
}
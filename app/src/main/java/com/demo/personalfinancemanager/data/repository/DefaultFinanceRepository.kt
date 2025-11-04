package com.demo.personalfinancemanager.data.repository

import com.demo.personalfinancemanager.data.model.BalanceDataPoint
import com.demo.personalfinancemanager.data.model.Category
import com.demo.personalfinancemanager.data.model.ChartPeriod
import com.demo.personalfinancemanager.data.model.Recipient
import com.demo.personalfinancemanager.data.model.Transaction
import com.demo.personalfinancemanager.data.model.TransactionType
import com.demo.personalfinancemanager.data.model.User
import com.demo.personalfinancemanager.data.source.MockDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Default implementation of [FinanceRepository].
 * Currently backed by [MockDataSource], can be replaced with Room/Network later.
 */
@Singleton
class DefaultFinanceRepository @Inject constructor() : FinanceRepository {

    override fun getCurrentUser(): Flow<User> = flow {
        delay(300)
        emit(MockDataSource.currentUser)
    }

    override fun getCategories(): Flow<List<Category>> = flow {
        delay(200)
        emit(MockDataSource.categories)
    }

    override fun getRecipients(): Flow<List<Recipient>> = flow {
        delay(200)
        emit(MockDataSource.recipients)
    }

    override fun getRecentTransactions(): Flow<List<Transaction>> = flow {
        delay(300)
        emit(MockDataSource.getRecentTransactions())
    }

    override fun getAllTransactions(): Flow<List<Transaction>> = flow {
        delay(300)
        emit(MockDataSource.transactions)
    }

    override fun getBalanceHistory(period: ChartPeriod): Flow<List<BalanceDataPoint>> = flow {
        delay(400)
        emit(MockDataSource.getBalanceHistory(period))
    }

    override fun calculateBalance(): Flow<Double> = flow {
        delay(200)
        emit(MockDataSource.calculateBalance())
    }

    override fun getTransactionById(transactionId: String): Flow<Transaction?> = flow {
        delay(200)
        emit(MockDataSource.transactions.find { it.id == transactionId })
    }

    override fun getTransactionsByCategory(categoryId: String): Flow<List<Transaction>> = flow {
        delay(300)
        emit(MockDataSource.transactions.filter { it.category.id == categoryId })
    }

    override fun getTransactionsByType(type: TransactionType): Flow<List<Transaction>> = flow {
        delay(300)
        emit(MockDataSource.transactions.filter { it.type == type })
    }
}

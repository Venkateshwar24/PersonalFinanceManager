package com.demo.personalfinancemanager.data.repository

import com.demo.personalfinancemanager.data.model.BalanceDataPoint
import com.demo.personalfinancemanager.data.model.Category
import com.demo.personalfinancemanager.data.model.ChartPeriod
import com.demo.personalfinancemanager.data.model.Recipient
import com.demo.personalfinancemanager.data.model.Transaction
import com.demo.personalfinancemanager.data.model.TransactionType
import com.demo.personalfinancemanager.data.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Repository contract that provides data to the ViewModel.
 * Abstracts data sources and enables easy testing by swapping implementations.
 */
interface FinanceRepository {

    /** Get the current user information. */
    fun getCurrentUser(): Flow<User>

    /** Get all available transaction categories. */
    fun getCategories(): Flow<List<Category>>

    /** Get all recipients for quick transfers. */
    fun getRecipients(): Flow<List<Recipient>>

    /** Get recent transactions (limited to most recent). */
    fun getRecentTransactions(): Flow<List<Transaction>>

    /** Get all transactions. */
    fun getAllTransactions(): Flow<List<Transaction>>

    /** Get balance history for a specific time period. */
    fun getBalanceHistory(period: ChartPeriod): Flow<List<BalanceDataPoint>>

    /** Calculate the current balance from transactions. */
    fun calculateBalance(): Flow<Double>

    /** Get transaction by ID (or null if not found). */
    fun getTransactionById(transactionId: String): Flow<Transaction?>

    /** Get transactions by category. */
    fun getTransactionsByCategory(categoryId: String): Flow<List<Transaction>>

    /** Get transactions by type (CREDIT or DEBIT). */
    fun getTransactionsByType(type: TransactionType): Flow<List<Transaction>>
}

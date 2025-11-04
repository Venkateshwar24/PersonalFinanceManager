package com.demo.personalfinancemanager.data.source

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.demo.personalfinancemanager.data.model.*
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

/**
 * Mock data source providing static data for the application
 * This simulates a database or API and can be easily replaced with Room later
 */
object MockDataSource {
    
    /**
     * Predefined categories for transactions
     */
    val categories = listOf(
        Category(
            id = "cat_food",
            name = "Food",
            icon = Icons.Default.Restaurant,
            type = TransactionType.DEBIT
        ),
        Category(
            id = "cat_transport",
            name = "Transport",
            icon = Icons.Default.DirectionsCar,
            type = TransactionType.DEBIT
        ),
        Category(
            id = "cat_shopping",
            name = "Shopping",
            icon = Icons.Default.ShoppingCart,
            type = TransactionType.DEBIT
        ),
        Category(
            id = "cat_entertainment",
            name = "Entertainment",
            icon = Icons.Default.Movie,
            type = TransactionType.DEBIT
        ),
        Category(
            id = "cat_salary",
            name = "Salary",
            icon = Icons.Default.AccountBalance,
            type = TransactionType.CREDIT
        ),
        Category(
            id = "cat_bank",
            name = "Bank",
            icon = Icons.Default.AccountBalance,
            type = TransactionType.CREDIT
        ),
        Category(
            id = "cat_investment",
            name = "Investment",
            icon = Icons.Default.TrendingUp,
            type = TransactionType.CREDIT
        )
    )
    
    /**
     * Mock recipients for quick transfers
     */
    val recipients = listOf(
        Recipient(
            id = "rec_1",
            name = "Sarah",
            avatarUrl = "https://i.pravatar.cc/150?img=1",
            isOnline = true
        ),
        Recipient(
            id = "rec_2",
            name = "Michael",
            avatarUrl = "https://i.pravatar.cc/150?img=2",
            isOnline = true
        ),
        Recipient(
            id = "rec_3",
            name = "Emma",
            avatarUrl = "https://i.pravatar.cc/150?img=3",
            isOnline = false
        ),
        Recipient(
            id = "rec_4",
            name = "James",
            avatarUrl = "https://i.pravatar.cc/150?img=4",
            isOnline = false
        ),
        Recipient(
            id = "rec_5",
            name = "Olivia",
            avatarUrl = "https://i.pravatar.cc/150?img=5",
            isOnline = true
        ),
        Recipient(
            id = "rec_6",
            name = "Olivia",
            avatarUrl = "https://i.pravatar.cc/150?img=5",
            isOnline = true
        ),
        Recipient(
            id = "rec_7",
            name = "Olivia",
            avatarUrl = "https://i.pravatar.cc/150?img=5",
            isOnline = true
        )

    )
    
    /**
     * Mock current user
     */
    val currentUser = User(
        id = "user_1",
        name = "John",
        email = "john@example.com",
        avatarUrl = "https://i.pravatar.cc/150?img=10",
        balance = 13553.00
    )
    
    /**
     * Mock transaction history
     */
    val transactions = listOf(
        Transaction(
            id = "txn_1",
            title = "Food",
            subtitle = "Payment",
            amount = 40.99,
            type = TransactionType.DEBIT,
            category = categories.first { it.id == "cat_food" },
            date = LocalDateTime.now().minusHours(2)
        ),
        Transaction(
            id = "txn_2",
            title = "AI-Bank",
            subtitle = "Deposit",
            amount = 460.00,
            type = TransactionType.CREDIT,
            category = categories.first { it.id == "cat_bank" },
            date = LocalDateTime.now().minusHours(5)
        ),
        Transaction(
            id = "txn_3",
            title = "Shopping",
            subtitle = "Payment",
            amount = 125.50,
            type = TransactionType.DEBIT,
            category = categories.first { it.id == "cat_shopping" },
            date = LocalDateTime.now().minusDays(1)
        ),
        Transaction(
            id = "txn_4",
            title = "Transport",
            subtitle = "Payment",
            amount = 15.00,
            type = TransactionType.DEBIT,
            category = categories.first { it.id == "cat_transport" },
            date = LocalDateTime.now().minusDays(1).minusHours(3)
        ),
        Transaction(
            id = "txn_5",
            title = "Salary",
            subtitle = "Deposit",
            amount = 3500.00,
            type = TransactionType.CREDIT,
            category = categories.first { it.id == "cat_salary" },
            date = LocalDateTime.now().minusDays(3)
        ),
        Transaction(
            id = "txn_6",
            title = "Entertainment",
            subtitle = "Payment",
            amount = 45.00,
            type = TransactionType.DEBIT,
            category = categories.first { it.id == "cat_entertainment" },
            date = LocalDateTime.now().minusDays(4)
        ),
        Transaction(
            id = "txn_7",
            title = "Food",
            subtitle = "Payment",
            amount = 32.75,
            type = TransactionType.DEBIT,
            category = categories.first { it.id == "cat_food" },
            date = LocalDateTime.now().minusDays(5)
        ),
        Transaction(
            id = "txn_8",
            title = "Investment",
            subtitle = "Deposit",
            amount = 500.00,
            type = TransactionType.CREDIT,
            category = categories.first { it.id == "cat_investment" },
            date = LocalDateTime.now().minusDays(7)
        )
    )
    
    /**
     * Generates mock balance data points for chart display
     * Creates a realistic balance history based on transactions
     * 
     * @param period The time period for which to generate data
     * @return List of balance data points
     */
    fun getBalanceHistory(period: ChartPeriod): List<BalanceDataPoint> {
        val now = LocalDateTime.now()
        val dataPoints = mutableListOf<BalanceDataPoint>()
        
        // Determine the range and interval based on period
        val (daysBack, intervalHours) = when (period) {
            ChartPeriod.ONE_DAY -> Pair(1, 2)      // Every 2 hours
            ChartPeriod.FIVE_DAYS -> Pair(5, 12)    // Every 12 hours
            ChartPeriod.ONE_MONTH -> Pair(30, 24)   // Daily
            ChartPeriod.THREE_MONTHS -> Pair(90, 72) // Every 3 days
            ChartPeriod.SIX_MONTHS -> Pair(180, 168) // Weekly
            ChartPeriod.ONE_YEAR -> Pair(365, 336)  // Every 2 weeks
        }
        
        // Starting balance (work backwards from current)
        var currentBalance = currentUser.balance
        
        // Generate data points
        var currentDate = now
        for (i in 0 until daysBack * 24 / intervalHours) {
            dataPoints.add(0, BalanceDataPoint(currentDate, currentBalance))
            currentDate = currentDate.minusHours(intervalHours.toLong())
            
            // Add some variation to make the chart interesting
            val variation = (-200..300).random().toDouble()
            currentBalance = (currentBalance - variation).coerceAtLeast(5000.0)
        }
        
        return dataPoints
    }
    
    /**
     * Returns recent transactions (limited to 10 most recent)
     */
    fun getRecentTransactions(): List<Transaction> {
        return transactions.sortedByDescending { it.date }.take(10)
    }
    
    /**
     * Calculates the total balance from starting balance and transactions
     */
    fun calculateBalance(): Double {
        val startingBalance = 10000.0
        val totalCredits = transactions.filter { it.type == TransactionType.CREDIT }.sumOf { it.amount }
        val totalDebits = transactions.filter { it.type == TransactionType.DEBIT }.sumOf { it.amount }
        return startingBalance + totalCredits - totalDebits
    }
}

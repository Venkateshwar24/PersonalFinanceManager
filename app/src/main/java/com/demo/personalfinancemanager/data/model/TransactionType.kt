package com.demo.personalfinancemanager.data.model

/**
 * Represents the type of financial transaction
 * CREDIT: Money coming in (deposits, income, transfers in)
 * DEBIT: Money going out (expenses, payments, transfers out)
 */
enum class TransactionType {
    CREDIT,  // Positive transactions (income)
    DEBIT    // Negative transactions (expenses)
}

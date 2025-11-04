package com.demo.personalfinancemanager.data.model

import java.time.LocalDateTime

/**
 * Represents a financial transaction
 * 
 * @property id Unique identifier for the transaction
 * @property title Main description of the transaction (e.g., "Food", "AI-Bank")
 * @property subtitle Additional details or subcategory (e.g., "Payment", "Deposit")
 * @property amount Transaction amount (positive for CREDIT, typically displayed with sign)
 * @property type Type of transaction (CREDIT or DEBIT)
 * @property category Associated category for the transaction
 * @property date Date and time when the transaction occurred
 * @property recipient Optional recipient for transfer transactions
 */
data class Transaction(
    val id: String,
    val title: String,
    val subtitle: String,
    val amount: Double,
    val type: TransactionType,
    val category: Category,
    val date: LocalDateTime,
    val recipient: Recipient? = null
)

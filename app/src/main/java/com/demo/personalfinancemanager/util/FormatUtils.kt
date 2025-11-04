package com.demo.personalfinancemanager.util

import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Utility object for formatting data for display
 */
object FormatUtils {
    
    /**
     * Formats a dollar amount with currency symbol
     * @param amount The amount to format
     * @return Formatted currency string (e.g., "$1,234.56")
     */
    fun formatCurrency(amount: Double): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale.US)
        return formatter.format(amount)
    }
    
    /**
     * Formats amount with sign for transactions
     * @param amount The transaction amount
     * @param isCredit Whether this is a credit (income) transaction
     * @return Formatted string with sign (e.g., "+ $460.00" or "- 40.99")
     */
    fun formatTransactionAmount(amount: Double, isCredit: Boolean): String {
        val sign = if (isCredit) "+ " else "- "
        return "$sign${formatCurrency(amount).replace("$", "")}"
    }
    
    /**
     * Formats a LocalDateTime to a relative or absolute time string
     * @param dateTime The datetime to format
     * @return Formatted string (e.g., "2 hours ago", "Yesterday", "Jan 15")
     */
    fun formatRelativeTime(dateTime: LocalDateTime): String {
        val now = LocalDateTime.now()
        val hoursDiff = java.time.Duration.between(dateTime, now).toHours()
        val daysDiff = java.time.Duration.between(dateTime, now).toDays()
        
        return when {
            hoursDiff < 1 -> "Just now"
            hoursDiff < 24 -> "$hoursDiff hours ago"
            daysDiff == 1L -> "Yesterday"
            daysDiff < 7 -> "$daysDiff days ago"
            else -> dateTime.format(DateTimeFormatter.ofPattern("MMM dd"))
        }
    }
    
    /**
     * Formats a date for display
     * @param dateTime The datetime to format
     * @param pattern The pattern to use (default: "MMM dd, yyyy")
     * @return Formatted date string
     */
    fun formatDate(dateTime: LocalDateTime, pattern: String = "MMM dd, yyyy"): String {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern))
    }
}

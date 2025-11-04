package com.demo.personalfinancemanager.data.model

import java.time.LocalDateTime

/**
 * Represents a data point in the balance chart
 * 
 * @property date The date and time of this data point
 * @property balance The account balance at this point in time
 */
data class BalanceDataPoint(
    val date: LocalDateTime,
    val balance: Double
)

/**
 * Enum representing different time periods for chart display
 */
enum class ChartPeriod(val label: String) {
    ONE_DAY("1D"),
    FIVE_DAYS("5D"),
    ONE_MONTH("1M"),
    THREE_MONTHS("3M"),
    SIX_MONTHS("6M"),
    ONE_YEAR("1Y")
}

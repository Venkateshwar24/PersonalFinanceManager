package com.demo.personalfinancemanager.ui.screens.home

import com.demo.personalfinancemanager.data.model.*

/**
 * Represents the UI state for the Home screen
 * This is a sealed interface to handle different states: Loading, Success, and Error
 */
sealed interface HomeUiState {
    /**
     * Initial loading state when data is being fetched
     */
    data object Loading : HomeUiState
    
    /**
     * Success state with all the data needed to display the home screen
     * 
     * @property user Current user information
     * @property balance Current account balance
     * @property recipients List of recipients for quick transfer
     * @property recentTransactions List of recent transactions to display
     * @property balanceHistory Balance data points for the chart
     * @property selectedPeriod Currently selected chart time period
     */
    data class Success(
        val user: User,
        val balance: Double,
        val recipients: List<Recipient>,
        val recentTransactions: List<Transaction>,
        val balanceHistory: List<BalanceDataPoint>,
        val selectedPeriod: ChartPeriod = ChartPeriod.ONE_MONTH
    ) : HomeUiState
    
    /**
     * Error state when data fetching fails
     * 
     * @property message Error message to display to the user
     */
    data class Error(val message: String) : HomeUiState
}

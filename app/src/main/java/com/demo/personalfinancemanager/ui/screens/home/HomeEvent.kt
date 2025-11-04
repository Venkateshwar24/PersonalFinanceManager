package com.demo.personalfinancemanager.ui.screens.home

import com.demo.personalfinancemanager.data.model.ChartPeriod

/**
 * User intents for the Home screen.
 * Drive all user interactions through a single event stream.
 */
sealed interface HomeEvent {
    data class PeriodSelected(val period: ChartPeriod) : HomeEvent
    data class TransactionClicked(val transactionId: String) : HomeEvent
    data class RecipientClicked(val recipientId: String) : HomeEvent
    data object NotificationClicked : HomeEvent
    data object Retry : HomeEvent
    data object Refresh : HomeEvent
}

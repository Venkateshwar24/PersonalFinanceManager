package com.demo.personalfinancemanager.ui.screens.home

/**
 * One-off UI effects that should be handled once by the UI layer.
 * Examples: toasts, navigation, snackbars.
 */
sealed interface HomeEffect {
    data class ShowToast(val message: String) : HomeEffect
    data class NavigateToTransaction(val transactionId: String) : HomeEffect
    data class NavigateToRecipient(val recipientId: String) : HomeEffect
    data class ShowError(val message: String) : HomeEffect
}

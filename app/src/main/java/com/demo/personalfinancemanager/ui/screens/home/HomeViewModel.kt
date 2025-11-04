package com.demo.personalfinancemanager.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.personalfinancemanager.data.model.BalanceDataPoint
import com.demo.personalfinancemanager.data.model.ChartPeriod
import com.demo.personalfinancemanager.data.repository.FinanceRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Home screen
 * Manages the UI state and handles business logic for the home screen
 * Follows MVVM architecture pattern with StateFlow for reactive UI updates
 */
class HomeViewModel @Inject constructor(
    private val repository: FinanceRepository
) : ViewModel() {
    
    // Private mutable state flow for internal updates
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    
    // Public immutable state flow for UI observation
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    // One-off effects (toasts, navigation)
    private val _effectFlow = MutableSharedFlow<HomeEffect>()
    val effectFlow: SharedFlow<HomeEffect> = _effectFlow.asSharedFlow()
    
    // In-memory cache so toggling periods shows identical data as before
    // (useful while using random mock data). Keyed by ChartPeriod.
    private val historyCache = mutableMapOf<ChartPeriod, List<BalanceDataPoint>>()
    
    init {
        // Load initial data when ViewModel is created
        loadHomeData()
    }
    
    /**
     * Loads all data required for the home screen
     * Combines multiple data streams into a single UI state
     */
    private fun loadHomeData(period: ChartPeriod = ChartPeriod.ONE_MONTH) {
        viewModelScope.launch {
            try {
                // Set loading state
                _uiState.value = HomeUiState.Loading
                
                // Fetch all required data in parallel using combine
                combine(
                    repository.getCurrentUser(),
                    repository.getRecipients(),
                    repository.getRecentTransactions(),
                    repository.getBalanceHistory(period)
                ) { user, recipients, transactions, balanceHistory ->
                    // Create success state with all the data
                    HomeUiState.Success(
                        user = user,
                        balance = user.balance,
                        recipients = recipients,
                        recentTransactions = transactions,
                        balanceHistory = balanceHistory,
                        selectedPeriod = period
                    )
                }.catch { exception ->
                    // Handle any errors during data fetching
                    _uiState.value = HomeUiState.Error(
                        message = exception.message ?: "An unknown error occurred"
                    )
                }.collect { successState ->
                    // Update UI state with fetched data and cache the series
                    if (successState is HomeUiState.Success) {
                        historyCache[successState.selectedPeriod] = successState.balanceHistory
                    }
                    _uiState.value = successState
                }
                
            } catch (e: Exception) {
                // Handle any unexpected errors
                _uiState.value = HomeUiState.Error(
                    message = e.message ?: "Failed to load data"
                )
            }
        }
    }

    /**
     * Single event entry point from UI.
     */
    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.PeriodSelected -> onPeriodSelected(event.period)
            is HomeEvent.TransactionClicked -> {
                viewModelScope.launch {
                    _effectFlow.emit(HomeEffect.NavigateToTransaction(event.transactionId))
                }
            }
            is HomeEvent.RecipientClicked -> {
                viewModelScope.launch {
                    _effectFlow.emit(HomeEffect.NavigateToRecipient(event.recipientId))
                }
            }
            HomeEvent.NotificationClicked -> {
                viewModelScope.launch {
                    _effectFlow.emit(HomeEffect.ShowToast("Notifications"))
                }
            }
            HomeEvent.Retry, HomeEvent.Refresh -> refresh()
        }
    }
    
    /**
     * Called when user selects a different time period for the chart
     * Updates only the balance history without reloading all data
     * 
     * @param period The newly selected chart period
     */
    fun onPeriodSelected(period: ChartPeriod) {
        val currentState = _uiState.value
        if (currentState is HomeUiState.Success && currentState.selectedPeriod != period) {
            // Update only the chart period and balance history without full reload
            viewModelScope.launch {
                try {
                    val cached = historyCache[period]
                    if (cached != null) {
                        _uiState.value = currentState.copy(
                            balanceHistory = cached,
                            selectedPeriod = period
                        )
                    } else {
                        repository.getBalanceHistory(period).collect { newHistory ->
                            historyCache[period] = newHistory
                            _uiState.value = currentState.copy(
                                balanceHistory = newHistory,
                                selectedPeriod = period
                            )
                        }
                    }
                } catch (e: Exception) {
                    // If fetching new history fails, keep current state
                    // In production, you might want to show a snackbar error
                    viewModelScope.launch {
                        _effectFlow.emit(HomeEffect.ShowError(e.message ?: "Failed to update chart"))
                    }
                }
            }
        }
    }
    
    /**
     * Refreshes all home screen data
     * Can be called by pull-to-refresh or manual refresh actions
     */
    fun refresh() {
        val currentPeriod = (_uiState.value as? HomeUiState.Success)?.selectedPeriod 
            ?: ChartPeriod.ONE_MONTH
        // On full refresh, clear the cache so new data is fetched
        historyCache.clear()
        loadHomeData(currentPeriod)
    }
    
    /**
     * Called when a transaction is clicked
     * Currently a placeholder for future navigation to transaction details
     * 
     * @param transactionId The ID of the clicked transaction
     */
    fun onTransactionClicked(transactionId: String) {
        // TODO: Navigate to transaction details screen
        // This will be implemented when we add navigation
    }
    
    /**
     * Called when a recipient is clicked
     * Currently a placeholder for future quick transfer functionality
     * 
     * @param recipientId The ID of the clicked recipient
     */
    fun onRecipientClicked(recipientId: String) {
        // TODO: Navigate to transfer screen or show quick transfer dialog
        // This will be implemented in future iterations
    }
}

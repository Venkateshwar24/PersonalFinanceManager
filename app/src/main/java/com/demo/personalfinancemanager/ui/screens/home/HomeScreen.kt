package com.demo.personalfinancemanager.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.demo.personalfinancemanager.ui.screens.home.components.*
import com.demo.personalfinancemanager.ui.theme.DividerColor

/**
 * Home screen - Main dashboard of the app
 * Displays balance, chart, recipients, and transaction history
 */
@Composable
fun HomeScreen(
    viewModelFactory: ViewModelProvider.Factory,
    viewModel: HomeViewModel = viewModel(factory = viewModelFactory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    
    // Collect one-off effects (toasts, navigation)
    LaunchedEffect(viewModel) {
        viewModel.effectFlow.collect { effect ->
            when (effect) {
                is HomeEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                is HomeEffect.NavigateToTransaction -> {
                    // TODO: Integrate with NavController when available
                    Toast.makeText(context, "Open transaction ${effect.transactionId}", Toast.LENGTH_SHORT).show()
                }
                is HomeEffect.NavigateToRecipient -> {
                    // TODO: Integrate with NavController when available
                    Toast.makeText(context, "Open recipient ${effect.recipientId}", Toast.LENGTH_SHORT).show()
                }
                is HomeEffect.ShowError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    // Handle different UI states without recreating scroll container
    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is HomeUiState.Loading -> {
                LoadingScreen()
            }
            is HomeUiState.Success -> {
                HomeContent(
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }
            is HomeUiState.Error -> {
                ErrorScreen(message = state.message, onRetry = viewModel::refresh)
            }
        }
    }
}

/**
 * Loading state UI
 */
@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

/**
 * Error state UI
 */
@Composable
private fun ErrorScreen(message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "⚠️",
                style = MaterialTheme.typography.displayMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Oops! Something went wrong",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}

/**
 * Main content when data is successfully loaded
 */
@Composable
private fun HomeContent(
    state: HomeUiState.Success,
    onEvent: (HomeEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            HomeHeader(
                userName = state.user.name,
                onNotificationClick = { onEvent(HomeEvent.NotificationClicked) }
            )
        }
        item { Spacer(modifier = Modifier.height(8.dp)) }
        item {
            Divider(
                modifier = Modifier.padding(horizontal = 20.dp),
                color = DividerColor,
                thickness = 1.dp
            )
        }
        item { Spacer(modifier = Modifier.height(12.dp)) }
        item {
            BalanceDisplay(balance = state.balance)
        }
        item { Spacer(modifier = Modifier.height(20.dp)) }
        item {
            ChartCard(
                balanceHistory = state.balanceHistory,
                selectedPeriod = state.selectedPeriod,
                onPeriodSelected = { onEvent(HomeEvent.PeriodSelected(it)) }
            )
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
        item {
            RecipientsSection(
                recipients = state.recipients,
                onRecipientClick = { onEvent(HomeEvent.RecipientClicked(it)) }
            )
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item {
            TransactionHistorySection(
                transactions = state.recentTransactions,
                onTransactionClick = { onEvent(HomeEvent.TransactionClicked(it)) }
            )
        }
    }
}

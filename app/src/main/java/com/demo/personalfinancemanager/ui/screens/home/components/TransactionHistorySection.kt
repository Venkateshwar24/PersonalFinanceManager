package com.demo.personalfinancemanager.ui.screens.home.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.dimensionResource
import com.demo.personalfinancemanager.ui.theme.amountLarge
import com.demo.personalfinancemanager.data.model.Transaction
import com.demo.personalfinancemanager.data.model.TransactionType
import com.demo.personalfinancemanager.util.FormatUtils

/**
 * Transaction history section displaying recent transactions
 * 
 * @param transactions List of transactions to display
 * @param onTransactionClick Callback when a transaction is clicked
 */
@Composable
fun TransactionHistorySection(
    transactions: List<Transaction>,
    onTransactionClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = com.demo.personalfinancemanager.R.dimen.spacing_sm))
    ) {
        // Section header with "Today" label (can be made dynamic later)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(id = com.demo.personalfinancemanager.R.dimen.inset_horizontal),
                    vertical = dimensionResource(id = com.demo.personalfinancemanager.R.dimen.spacing_sm)
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = com.demo.personalfinancemanager.R.string.transactions_history_title),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Text(
                text = stringResource(id = com.demo.personalfinancemanager.R.string.today),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        // List of transactions (using Column instead of LazyColumn to avoid nested scrolling issues)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = com.demo.personalfinancemanager.R.dimen.spacing_md))
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(id = com.demo.personalfinancemanager.R.dimen.spacing_sm)))
            transactions.forEach { transaction ->
                TransactionItem(
                    transaction = transaction,
                    onClick = { onTransactionClick(transaction.id) },
                    modifier = Modifier.padding(horizontal = dimensionResource(id = com.demo.personalfinancemanager.R.dimen.inset_horizontal))
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = com.demo.personalfinancemanager.R.dimen.spacing_sm)))
        }
    }
}

/**
 * Individual transaction item
 */
@Composable
private fun TransactionItem(
    transaction: Transaction,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current,
                onClick = onClick
            )
            .padding(vertical = dimensionResource(id = com.demo.personalfinancemanager.R.dimen.spacing_sm)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side: Icon + Transaction details
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = com.demo.personalfinancemanager.R.dimen.spacing_md)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon container
            Box(
                modifier = Modifier
                    .size(dimensionResource(id = com.demo.personalfinancemanager.R.dimen.size_avatar))
                    .clip(RoundedCornerShape(dimensionResource(id = com.demo.personalfinancemanager.R.dimen.corner_md)))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                transaction.category.icon?.let { icon ->
                    Icon(
                        imageVector = icon,
                        contentDescription = transaction.category.name,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(dimensionResource(id = com.demo.personalfinancemanager.R.dimen.size_icon_sm))
                    )
                }
            }
            
            // Transaction details
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = com.demo.personalfinancemanager.R.dimen.spacing_xs))
            ) {
                Text(
                    text = transaction.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = transaction.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        // Right side: Amount
        Text(
            text = FormatUtils.formatTransactionAmount(
                transaction.amount,
                transaction.type == TransactionType.CREDIT
            ),
            style = MaterialTheme.typography.amountLarge,
            // As per design: credits (deposits) are black, debits (payments) are gray
            color = if (transaction.type == TransactionType.CREDIT)
                MaterialTheme.colorScheme.onBackground
            else
                MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

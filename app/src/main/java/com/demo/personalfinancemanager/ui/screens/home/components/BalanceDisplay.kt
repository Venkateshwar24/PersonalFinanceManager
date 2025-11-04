package com.demo.personalfinancemanager.ui.screens.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.demo.personalfinancemanager.util.FormatUtils
import com.demo.personalfinancemanager.ui.theme.displayMediumLarge

/**
 * Balance display component
 * Shows the current account balance prominently
 * 
 * @param balance The current balance amount
 */
@Composable
fun BalanceDisplay(
    balance: Double,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = com.demo.personalfinancemanager.R.dimen.inset_horizontal)),
        verticalAlignment = Alignment.Bottom
    ) {
        // Balance amount - large and prominent
        Text(
            text = FormatUtils.formatCurrency(balance),
            style = MaterialTheme.typography.displayMediumLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.alignByBaseline()
        )

        Spacer(modifier = Modifier.width(dimensionResource(id = com.demo.personalfinancemanager.R.dimen.spacing_sm)))

        // "Balance" label shown on the same line to the right
        Text(
            text = stringResource(id = com.demo.personalfinancemanager.R.string.balance_label),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.alignByBaseline()
        )
    }
}

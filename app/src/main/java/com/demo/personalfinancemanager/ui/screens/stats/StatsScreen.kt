package com.demo.personalfinancemanager.ui.screens.stats

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.dimensionResource

/**
 * Placeholder screen for Statistics/Analytics
 * This will be implemented in future iterations
 */
@Composable
fun StatsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = com.demo.personalfinancemanager.R.dimen.spacing_lg)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "📊",
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = com.demo.personalfinancemanager.R.dimen.spacing_lg)))
            Text(
                text = stringResource(id = com.demo.personalfinancemanager.R.string.statistics_title),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = com.demo.personalfinancemanager.R.dimen.spacing_sm)))
            Text(
                text = stringResource(id = com.demo.personalfinancemanager.R.string.coming_soon),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

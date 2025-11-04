package com.demo.personalfinancemanager.ui.screens.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.dimensionResource

/**
 * Header component displaying welcome message and notification bell
 * 
 * @param userName The name of the current user
 * @param onNotificationClick Callback when notification bell is clicked
 */
@Composable
fun HomeHeader(
    userName: String,
    onNotificationClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = com.demo.personalfinancemanager.R.dimen.inset_horizontal),
                vertical = dimensionResource(id = com.demo.personalfinancemanager.R.dimen.spacing_lg)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Welcome text
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Normal
                    )
                ) {
                    append(stringResource(id = com.demo.personalfinancemanager.R.string.welcome))
                }
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(userName)
                }
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(stringResource(id = com.demo.personalfinancemanager.R.string.exclamation))
                }
            },
            style = MaterialTheme.typography.titleLarge
        )
        
        // Notification bell icon button
        IconButton(
            onClick = onNotificationClick,
            modifier = Modifier.size(dimensionResource(id = com.demo.personalfinancemanager.R.dimen.corner_pill))
        ) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = stringResource(id = com.demo.personalfinancemanager.R.string.notifications),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

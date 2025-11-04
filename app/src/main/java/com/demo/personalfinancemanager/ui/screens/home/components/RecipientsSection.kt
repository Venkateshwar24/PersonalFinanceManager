package com.demo.personalfinancemanager.ui.screens.home.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.demo.personalfinancemanager.data.model.Recipient

/**
 * Recipients section showing horizontal scrollable list of contacts
 * 
 * @param recipients List of recipients to display
 * @param onRecipientClick Callback when a recipient is clicked
 */
@Composable
fun RecipientsSection(
    recipients: List<Recipient>,
    onRecipientClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        // Section title
        Text(
            text = "Recipients",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
        )
        
        // Horizontal scrollable list of recipients using Row instead of LazyRow
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            recipients.forEach { recipient ->
                RecipientItem(
                    recipient = recipient,
                    onClick = { onRecipientClick(recipient.id) }
                )
            }
        }
    }
}

/**
 * Individual recipient item with avatar
 */
@Composable
private fun RecipientItem(
    recipient: Recipient,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(60.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current,
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // Avatar image
        Box(
            modifier = Modifier
                .size(56.dp)
                // Draw circular background but don't clip children so badge isn't hidden
                .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
        ) {
            AsyncImage(
                model = recipient.avatarUrl,
                contentDescription = recipient.name,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            // Online/offline status badge (bottom-right dot)
            run {
                val badgeSize = 12.dp
                val ringColor = MaterialTheme.colorScheme.background
                val ringStrokeWidth = 2.dp
                val statusColor = if (recipient.isOnline) Color(0xFF4CAF50) else Color(0xFFFF9800) // green or orange
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(badgeSize)
                        .clip(CircleShape)
                        .background(statusColor)
                        .drawBehind {
                            val stroke = ringStrokeWidth.toPx()
                            drawCircle(
                                color = ringColor,
                                radius = (size.minDimension / 2f) + stroke / 2f,
                                center = center,
                                style = androidx.compose.ui.graphics.drawscope.Stroke(width = stroke)
                            )
                        }
                )
            }
        }
        
        // Recipient name
        Text(
            text = recipient.name,
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 11.sp
            ),
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

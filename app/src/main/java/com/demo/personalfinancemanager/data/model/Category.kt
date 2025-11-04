package com.demo.personalfinancemanager.data.model

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Represents a transaction category with its visual representation
 * 
 * @property id Unique identifier for the category
 * @property name Display name of the category (e.g., "Food", "Transport")
 * @property icon Vector icon representing the category
 * @property iconRes Alternative drawable resource ID for the icon
 * @property type Whether this category is typically for CREDIT or DEBIT transactions
 */
data class Category(
    val id: String,
    val name: String,
    val icon: ImageVector? = null,
    val iconRes: Int? = null,
    val type: TransactionType
)

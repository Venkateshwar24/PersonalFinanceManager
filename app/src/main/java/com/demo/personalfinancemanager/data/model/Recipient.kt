package com.demo.personalfinancemanager.data.model

/**
 * Represents a recipient/contact for quick transfers
 * 
 * @property id Unique identifier for the recipient
 * @property name Display name of the recipient
 * @property avatarUrl URL to the recipient's avatar/profile picture
 * @property isOnline Whether the recipient is currently online (for future features)
 */
data class Recipient(
    val id: String,
    val name: String,
    val avatarUrl: String? = null,
    val isOnline: Boolean = false
)

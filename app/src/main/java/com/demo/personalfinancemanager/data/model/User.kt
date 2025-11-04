package com.demo.personalfinancemanager.data.model

/**
 * Represents the current user of the application
 * 
 * @property id Unique identifier for the user
 * @property name Display name of the user (e.g., "John")
 * @property email User's email address
 * @property avatarUrl URL to the user's profile picture
 * @property balance Current account balance
 */
data class User(
    val id: String,
    val name: String,
    val email: String? = null,
    val avatarUrl: String? = null,
    val balance: Double
)

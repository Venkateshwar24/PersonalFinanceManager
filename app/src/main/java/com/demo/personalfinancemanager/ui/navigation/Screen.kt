package com.demo.personalfinancemanager.ui.navigation

/**
 * Sealed class representing all navigation destinations in the app
 * Each destination has a route string used for navigation
 */
sealed class Screen(val route: String) {
    /**
     * Home screen - main dashboard
     */
    data object Home : Screen("home")
    
    /**
     * Statistics screen - analytics and reports
     */
    data object Stats : Screen("stats")
    
    /**
     * Profile screen - user settings and profile
     */
    data object Profile : Screen("profile")
}

/**
 * List of bottom navigation items
 * Used to configure the bottom navigation bar
 */
val bottomNavigationItems = listOf(
    Screen.Home,
    Screen.Stats,
    Screen.Profile
)

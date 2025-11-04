package com.demo.personalfinancemanager.ui

import android.widget.Toast
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.demo.personalfinancemanager.ui.navigation.Screen
import com.demo.personalfinancemanager.ui.navigation.bottomNavigationItems
import com.demo.personalfinancemanager.ui.screens.home.HomeScreen
import com.demo.personalfinancemanager.ui.screens.profile.ProfileScreen
import com.demo.personalfinancemanager.ui.screens.stats.StatsScreen
import com.demo.personalfinancemanager.ui.theme.BottomNavBackground
import com.demo.personalfinancemanager.ui.theme.BottomNavSelected
import com.demo.personalfinancemanager.ui.theme.BottomNavUnselected
import com.demo.personalfinancemanager.ui.theme.FABBackground

/**
 * Main screen container with bottom navigation
 * This is the root of the app's UI after the theme is applied
 */
@Composable
fun PersonalFinanceManagerScreen(viewModelFactory: ViewModelProvider.Factory) {
    val navController = rememberNavController()
    val context = LocalContext.current
    
    Scaffold(
        bottomBar = {
            PillBottomBar(
                navController = navController,
                onFabClick = {
                    Toast.makeText(
                        context,
                        context.getString(com.demo.personalfinancemanager.R.string.add_transaction),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    ) { paddingValues ->
        NavigationHost(
            navController = navController,
            modifier = Modifier.padding(paddingValues),
            viewModelFactory = viewModelFactory
        )
    }
}

/**
 * Centered pill-shaped bottom navigation bar (not full width)
 */
@Composable
private fun PillBottomBar(navController: NavHostController, onFabClick: () -> Unit) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = com.demo.personalfinancemanager.R.dimen.spacing_lg),
                vertical = dimensionResource(id = com.demo.personalfinancemanager.R.dimen.spacing_sm)
            )
            .navigationBarsPadding()
    ) {
        Surface(
            modifier = Modifier
                .align(Alignment.CenterStart),
            shape = RoundedCornerShape(dimensionResource(id = com.demo.personalfinancemanager.R.dimen.corner_pill)),
            color = BottomNavBackground,
            tonalElevation = dimensionResource(id = com.demo.personalfinancemanager.R.dimen.elevation_tonal),
            shadowElevation = dimensionResource(id = com.demo.personalfinancemanager.R.dimen.elevation_shadow)
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(id = com.demo.personalfinancemanager.R.dimen.spacing_md),
                        vertical = dimensionResource(id = com.demo.personalfinancemanager.R.dimen.spacing_sm)
                    ),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = com.demo.personalfinancemanager.R.dimen.spacing_xl)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                bottomNavigationItems.forEach { screen ->
                    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                    val tint = if (selected) Color.Black else BottomNavUnselected
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(dimensionResource(id = com.demo.personalfinancemanager.R.dimen.corner_xl)))
                            .background(
                                color = if (selected) Color.White else Color.Transparent,
                                shape = RoundedCornerShape(dimensionResource(id = com.demo.personalfinancemanager.R.dimen.corner_xl))
                            )
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = LocalIndication.current
                            ) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                            .padding(
                                horizontal = dimensionResource(id = com.demo.personalfinancemanager.R.dimen.spacing_md),
                                vertical = dimensionResource(id = com.demo.personalfinancemanager.R.dimen.spacing_sm)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = com.demo.personalfinancemanager.R.dimen.spacing_md) / 2)
                    ) {
                        Icon(
                            imageVector = getIconForScreen(screen),
                            contentDescription = when (screen) {
                                Screen.Home -> stringResource(id = com.demo.personalfinancemanager.R.string.nav_home)
                                Screen.Stats -> stringResource(id = com.demo.personalfinancemanager.R.string.nav_stats)
                                Screen.Profile -> stringResource(id = com.demo.personalfinancemanager.R.string.nav_profile)
                            },
                            tint = tint
                        )
                        if (selected) {
                            Text(
                                text = when (screen) {
                                    Screen.Home -> stringResource(id = com.demo.personalfinancemanager.R.string.nav_home)
                                    Screen.Stats -> stringResource(id = com.demo.personalfinancemanager.R.string.nav_stats)
                                    Screen.Profile -> stringResource(id = com.demo.personalfinancemanager.R.string.nav_profile)
                                },
                                    color = tint,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }
            }
        }

        // Floating action button aligned to the same vertical centerline as the pill bar
        FloatingActionButton(
            onClick = onFabClick,
            modifier = Modifier.align(Alignment.CenterEnd),
            shape = RoundedCornerShape(dimensionResource(id = com.demo.personalfinancemanager.R.dimen.corner_pill)),
            containerColor = FABBackground,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(id = com.demo.personalfinancemanager.R.string.add_transaction)
            )
        }
    }
}

/**
 * Navigation host that manages navigation between screens
 * Using enterTransition and exitTransition set to None to avoid animation-related layout issues
 */
@Composable
private fun NavigationHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModelFactory: ViewModelProvider.Factory
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable(Screen.Home.route) {
            HomeScreen(viewModelFactory = viewModelFactory)
        }
        composable(Screen.Stats.route) {
            StatsScreen()
        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
    }
}

/**
 * Returns the appropriate icon for each screen
 */
private fun getIconForScreen(screen: Screen): ImageVector {
    return when (screen) {
        Screen.Home -> Icons.Default.Home
        Screen.Stats -> Icons.Default.BarChart
        Screen.Profile -> Icons.Default.Person
    }
}

/**
 * Returns the label for each screen to display beside the icon when selected
 */
private fun getLabelForScreen(screen: Screen): String = when (screen) {
    Screen.Home -> "home"
    Screen.Stats -> "stats"
    Screen.Profile -> "profile"
}

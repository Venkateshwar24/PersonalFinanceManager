package com.demo.personalfinancemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.demo.personalfinancemanager.ui.theme.PersonalFinanceManagerTheme
import androidx.compose.material3.Surface
import com.demo.personalfinancemanager.ui.MainScreen
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * Main Activity - Entry point of the Personal Finance Manager app
 * Sets up the Compose UI with Material3 theming and edge-to-edge display
 */
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable edge-to-edge display for modern Android UI
        enableEdgeToEdge()
        // Inject dependencies
        (application as PersonalFinanceManagerApp).appComponent.inject(this)
        
        setContent {
            PersonalFinanceManagerTheme {
                Surface {
                    MainScreen(viewModelFactory)
                }
            }
        }
    }
}
package com.example.nova_cdi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.nova_cdi.ui.theme.Nova_cdiTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.nova_cdi.navigation.NavigationGraph


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {

            val navController = rememberNavController()

            Nova_cdiTheme {
                NavHost(navController = navController, graph = NavigationGraph(navController))
            }

        }
    }
}
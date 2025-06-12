package com.example.nova_cdi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.nova_cdi.chart.TelaComGrafico
import com.example.nova_cdi.ui.theme.Nova_cdiTheme
import com.example.nova_cdi.startscreen.InovaCDIScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Nova_cdiTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        InovaCDIScreen(navController)
                    }
                    composable("Chart") {
                        TelaComGrafico(navController)
                    }
                }
            }
        }
    }
}
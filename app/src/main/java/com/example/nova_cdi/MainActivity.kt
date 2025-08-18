package com.example.nova_cdi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.nova_cdi.ui.theme.Nova_cdiTheme
import com.example.nova_cdi.startscreen.InputScreen
import com.example.nova_cdi.startscreen.InovaCDIScreen
import com.example.nova_cdi.startscreen.TelaComGrafico
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Nova_cdiTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "input") {
                    // Tela de input do IP
                    composable("input") {
                        InputScreen(onIpEntered = { ip ->
                            navController.navigate("home/$ip") {
                                popUpTo("input") { inclusive = true }
                            }
                        })
                    }
                    // Tela principal que recebe IP na rota
                    composable(
                        route = "home/{ip}",
                        arguments = listOf(navArgument("ip") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val ip = backStackEntry.arguments?.getString("ip") ?: ""
                        InovaCDIScreen(navController, ip)
                    }
                    // Tela do gráfico, também com IP
                    composable(
                        route = "chart/{ip}",
                        arguments = listOf(navArgument("ip") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val ip = backStackEntry.arguments?.getString("ip") ?: ""
                        TelaComGrafico(navController, ip)
                    }
                }
            }
        }
    }
}

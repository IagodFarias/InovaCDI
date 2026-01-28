package com.example.nova_cdi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.nova_cdi.Graficos.*
import com.example.nova_cdi.login.TelaCadastro
import com.example.nova_cdi.ui.theme.Nova_cdiTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.example.nova_cdi.login.*



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            Nova_cdiTheme {
                NavHost(navController = navController, graph = NavigationGraph(navController))
            }

        }
    }
}










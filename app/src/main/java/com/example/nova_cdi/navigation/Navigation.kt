package com.example.nova_cdi.navigation


import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import com.example.nova_cdi.chart.TelaComGrafico
import com.example.nova_cdi.home.TelaHome
import com.example.nova_cdi.login.TelaAcesso
import com.example.nova_cdi.login.TelaCadastro

fun NavigationGraph(navController: NavController): NavGraph{
    return navController.createGraph(startDestination = "Acesso") {
            composable("Acesso") { TelaAcesso(navController) }
            composable("Cadastro") { TelaCadastro(navController) }
            composable("Home") { TelaHome(navController) }
            composable("Graficos") { TelaComGrafico(navController) }
    }
}
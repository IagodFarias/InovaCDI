package com.example.nova_cdi.navigation


import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import com.example.nova_cdi.Graficos.CondGraph
import com.example.nova_cdi.Graficos.ConsGraph
import com.example.nova_cdi.Graficos.CorrGraph
import com.example.nova_cdi.Graficos.TelaGrafico
import com.example.nova_cdi.Graficos.TenGraph
import com.example.nova_cdi.home.Chat.TelaChat
import com.example.nova_cdi.home.Notifications.TelaAlertas
import com.example.nova_cdi.home.Search.TelaInformações
import com.example.nova_cdi.home.TelaHome
import com.example.nova_cdi.login.TelaAcesso
import com.example.nova_cdi.login.TelaCadastro

fun NavigationGraph(navController: NavController): NavGraph{
    return navController.createGraph(startDestination = "Acesso") {
            composable("Acesso") { TelaAcesso(navController) }
            composable("Cadastro") { TelaCadastro(navController) }
            composable("Home") { TelaHome(navController)}
            composable("Chat"){ TelaChat(navController) }
            composable("Alertas") { TelaAlertas(navController) }
            composable("Informacoes") { TelaInformações(navController) }
            composable("Grafico_Corrente") { CorrGraph(navController) }
            composable("Grafico_Condutividade") { CondGraph(navController) }
            composable("Grafico_Tensao") { TenGraph(navController) }
            composable("Grafico_Consumo") { ConsGraph(navController) }
    }
}

fun NavController.currentRoute(): String? {
    return this.currentBackStackEntry?.destination?.route
}

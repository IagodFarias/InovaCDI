package com.example.nova_cdi.home.Notifications

import EspaçamentoLaterial
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nova_cdi.home.BottomBar
import com.example.nova_cdi.home.Chat.GenericTopBar
import com.example.nova_cdi.home.Chat.TelaChat


@Composable
fun TelaAlertas(navController: NavController) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.safeDrawing.asPaddingValues()),

        topBar = {
            GenericTopBar(
                navController,
                "Alertas"
            )
        },
        bottomBar = {
            BottomBar(navController)
        }

    ){
            innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(EspaçamentoLaterial)
            //.fillMaxSize()
            ,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.padding(10.dp))

            //TODO: CHAT, PESQUISAR COMO FAZER

        }

    }
}



@Preview(name = "Alerta")
@Composable
private fun PreviewAlerta() {
    val navController = rememberNavController()

    TelaAlertas(navController)
}
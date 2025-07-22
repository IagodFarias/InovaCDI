package com.example.nova_cdi.home.Chat

import BoxWidhtSize
import EspaçamentoLaterial
import FonteGrande
import FontePadrao
import TamanhoIcones
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nova_cdi.Graficos.WifiInfo
import com.example.nova_cdi.home.BottomBar
import com.example.nova_cdi.home.HomeTopBar
import com.example.nova_cdi.home.InformationBox
import com.example.nova_cdi.home.TelaHome
import com.example.nova_cdi.login.AcessoTexto
import com.example.nova_cdi.ui.theme.Blue
import com.example.nova_cdi.ui.theme.DarkBlue
import com.example.nova_cdi.ui.theme.Gray
import com.example.nova_cdi.ui.theme.LeafGreen
import com.example.nova_cdi.ui.theme.NeutralBlue

import com.example.nova_cdi.ui.theme.WhiteLeafGreen

@Composable
fun TelaChat(navController: NavController) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.safeDrawing.asPaddingValues()),

        topBar = {
            GenericTopBar(
                navController,
                "Suporte"
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


@Composable
fun GenericTopBar(
    navController: NavController,
    text : String
){
    Box(
        modifier = Modifier
            .width(BoxWidhtSize)
            .background(
                color = WhiteLeafGreen,
            )


    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft, //TODO: MELHORAR CIONE
                    tint = LeafGreen,
                    modifier = Modifier
                        .width(TamanhoIcones.times(5))
                        .height(TamanhoIcones.times(5)),
                    contentDescription = "(+)"
                )
            }

            Text(
                text = text,
                fontSize = FontePadrao,
                fontWeight = FontWeight.Bold,
                color = NeutralBlue
            )

            Box(
                modifier = Modifier
                    .width(TamanhoIcones.times(2))
                    .height(TamanhoIcones.times(2))
            )
        }
    }
}


@Preview(name = "Chat")
@Composable
private fun PreviewChat() {
    val navController = rememberNavController()

    TelaChat(navController)
}
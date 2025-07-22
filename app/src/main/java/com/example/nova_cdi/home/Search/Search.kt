package com.example.nova_cdi.home.Search


import BoxWidhtSize
import EspaçamentoLaterial
import FontePadrao
import TamanhoIcones
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nova_cdi.R
import com.example.nova_cdi.home.BottomBar
import com.example.nova_cdi.home.Chat.GenericTopBar
import com.example.nova_cdi.home.Chat.TelaChat
import com.example.nova_cdi.ui.theme.Blue
import com.example.nova_cdi.ui.theme.LeafGreen
import com.example.nova_cdi.ui.theme.NeutralBlue
import com.example.nova_cdi.ui.theme.WhiteLeafGreen


@Composable
fun TelaInformações(navController: NavController) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.safeDrawing.asPaddingValues()),

        topBar = {
            InformaçõesTopBar()
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

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(innerPadding)
                    .padding(EspaçamentoLaterial)
                    .verticalScroll(rememberScrollState())
                ,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                InfoItens(
                    navController,
                    "Teste",
                    R.drawable.notifications
                )

                InfoItens(
                    navController,
                    "Teste",
                    R.drawable.notifications
                )

                InfoItens(
                    navController,
                    "Teste",
                    R.drawable.notifications
                )

                InfoItens(
                    navController,
                    "Teste",
                    R.drawable.notifications
                )

                InfoItens(
                    navController,
                    "Teste",
                    R.drawable.notifications
                )

            }

        }

    }
}

@Composable
fun InformaçõesTopBar(){
    Box(
        modifier = Modifier
            .background(
                color = WhiteLeafGreen,
                shape = RoundedCornerShape(15.dp)
            )
            .width(BoxWidhtSize)
            .height(60.dp)
    ){}
}

@Composable
fun InfoItens(
    navController: NavController,
    texto : String,
    icone : Int
){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ){
        Icon(
            painter = painterResource(id = icone),
            tint = NeutralBlue,
            modifier = Modifier
                .width(TamanhoIcones.times(0.7F))
                .height(TamanhoIcones.times(0.7F)),
            contentDescription = "."
        )

        Text(
            text = texto,
            fontSize = FontePadrao,
            fontWeight = FontWeight.Bold,
            color = NeutralBlue
        )
    }
}


@Preview(name = "Alerta")
@Composable
private fun PreviewInformaões() {
    val navController = rememberNavController()

    TelaInformações(navController)
}
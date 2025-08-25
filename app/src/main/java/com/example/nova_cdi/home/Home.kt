package com.example.nova_cdi.home

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nova_cdi.Graficos.WifiInfo
import com.example.nova_cdi.R
import com.example.nova_cdi.navigation.currentRoute
import com.example.nova_cdi.ui.theme.Blue
import com.example.nova_cdi.ui.theme.DarkBlue
import com.example.nova_cdi.ui.theme.Gray
import com.example.nova_cdi.ui.theme.Green
import com.example.nova_cdi.ui.theme.NeutralBlue
import java.text.DecimalFormat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaHome(navController: NavController) {
    val context = LocalContext.current


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.safeDrawing.asPaddingValues()),

        topBar = {
            HomeTopBar(navController)
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
            ,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Spacer(modifier = Modifier.padding(10.dp))

            Box(
                modifier = Modifier
                    .background(
                        color = Blue,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .width(BoxWidhtSize)
                    .height(75.dp)
            ){
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Box(){
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                            ,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        )
                        {
                            Icon( //TODO: ENCONTRAR ICONE DE PAINES
                                imageVector = Icons.Default.Home,
                                tint = Color.Black,
                                modifier = Modifier
                                    .width(TamanhoIcones)
                                    .height(TamanhoIcones),
                                contentDescription = "Home"
                            )
                            Text(
                                text = "Operando",
                                fontSize = FonteGrande,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }


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


                InformationBox(
                    informacao = "Nível de água",
                    valor = 10.055F,
                    medida = "%",
                    icone = R.drawable.water,
                    Color(0xFF005AA0)
                )


                InformationBox(
                    informacao = "Condutividade",
                    valor = 450F,
                    medida = "mS|cm",
                    icone = R.drawable.spark,
                    Color(0xFFFEC420)
                )

                InformationBox(
                    informacao = "Condutividade",
                    valor = 450F,
                    medida = "mS|cm",
                    icone = R.drawable.spark,
                    Color(0xFFFEC420)
                )

                InformationBox(
                    informacao = "Condutividade",
                    valor = 450F,
                    medida = "mS|cm",
                    icone = R.drawable.spark,
                    Color(0xFFFEC420)
                )

                InformationBox(
                    informacao = "Condutividade",
                    valor = 450F,
                    medida = "mS|cm",
                    icone = R.drawable.spark,
                    Color(0xFFFEC420)
                )

                InformationBox(
                    informacao = "Condutividade",
                    valor = 450F,
                    medida = "mS|cm",
                    icone = R.drawable.spark,
                    Color(0xFFFEC420)
                )

                InformationBox(
                    informacao = "Condutividade",
                    valor = 450F,
                    medida = "mS|cm",
                    icone = R.drawable.spark,
                    Color(0xFFFEC420)
                )


            }




            Button(
                onClick = {
                    if(WifiInfo.isConnectedToWifi(context)){
                        navController.navigate("Graficos")
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkBlue,
                    contentColor = Gray
                ),
                shape = RoundedCornerShape(10.dp),
            ) {
                Text(
                    text = "Gráficos",
                    fontSize = FontePadrao
                )
            }



        }


    }
}

@Composable
fun BottomBar(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background( color = Green)
            .border(
                width = 0.1.dp,
                color = Color.Gray,
            )

    ){
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                onClick = {

                    navController.navigate("Informacoes") {
                        popUpTo("Informacoes") {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }


            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = Color.White,
                    modifier = Modifier
                            .width(TamanhoIcones)
                            .height(TamanhoIcones),
                    contentDescription = "Pesquisa"
                )
            }


            IconButton(
                onClick = {
                    if(navController.currentRoute() != "Home"){
                        navController.navigate("Home") {
                            popUpTo("Home") {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    tint = Color.White,
                    modifier = Modifier
                            .width(TamanhoIcones)
                            .height(TamanhoIcones),
                    contentDescription = "Home"
                )
            }

            IconButton(
                onClick = {
                    //TODO (+) - NÃO SEI OQ ISSO DEVERIA SIGNIFICAR :/
                }
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    tint = Color.White,
                    modifier = Modifier
                            .width(TamanhoIcones)
                            .height(TamanhoIcones),
                    contentDescription = "(+)"
                )
            }

            IconButton(
                onClick = {
                    navController.navigate("Chat") {
                        popUpTo("Chat") {
                            inclusive = false // mantém "Home" na pilha
                        }
                        launchSingleTop = true
                    }
                }
            ){
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Assisten virtual",
                    tint = Color.White,
                    modifier = Modifier
                            .clip(CircleShape)
                            .width(TamanhoIcones)
                            .height(TamanhoIcones)
                )
            }

        }
    }
}


@Composable
fun HomeTopBar(navController: NavController){

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        Text(
            text = "InovaCDI",
            fontSize = FonteGrande,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )
        Box(){
            Row(){
                IconButton(
                    onClick = {
                        navController.navigate("Alertas")
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.notifications),
                        contentDescription = "User",
                        modifier = Modifier
                            .width(TamanhoIcones)
                            .height(TamanhoIcones),
                        tint = NeutralBlue
                    )
                }

                IconButton(
                    onClick = {
                        ///TODO USUÁRIO
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "User",
                        tint = Color.Black,
                        modifier = Modifier
                            .clip(CircleShape)
                    )
                }
            }
        }
    }

}

@Composable
fun InformationBox(
    informacao: String,
    valor: Float,
    medida: String,
    icone: Int,
    color: Color
){
    Box(
        modifier = Modifier
            .width(BoxWidhtSize)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                color = Color.Black,
                shape = RoundedCornerShape(10.dp),
                width = 1.dp
            )
            .padding(vertical = 15.dp),
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            //IMAGEM
            Icon(
                painter = painterResource(id = icone),
                contentDescription = "User",
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp),
                tint = color
            )


            //INFORMAÇÃO
            Column() {
                Text(
                    text = informacao,
                    fontSize = FontePadrao,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = medida,
                    fontSize = FontePadrao,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            //VALOR
            Box(){
                Text(
                    text = DecimalFormat(".##").format(valor),
                    fontSize = FonteGrande,
                    fontWeight = FontWeight.ExtraBold
                )
            }

        }
    }
}

@Preview(name = "Home")
@Composable
private fun PreviewHome() {
    val navController = rememberNavController()


    TelaHome(navController)
}
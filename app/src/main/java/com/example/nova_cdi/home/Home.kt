package com.example.nova_cdi.home

import BoxWidhtSize
import EspaçamentoLaterial
import FonteGrande
import FontePadrao
import TamanhoIcones
import android.R
import android.graphics.drawable.Icon
import android.media.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold


import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.traceEventStart
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nova_cdi.login.AcessoTexto
import com.example.nova_cdi.ui.theme.Blue
import com.example.nova_cdi.ui.theme.DarkBlue
import com.example.nova_cdi.ui.theme.Gray
import com.example.nova_cdi.ui.theme.Green
import java.text.DecimalFormat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaHome(navController: NavController) {
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
            //.fillMaxSize()
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
                //TODO COLOCAR OS ICONES
            }

            Spacer(modifier = Modifier.padding(10.dp))

            InformationBox(
                informacao = "Nível de água",
                valor = 10.055F,
                medida = "%",
                icone = Icons.Default.Add
            )


            InformationBox(
                informacao = "Condutividade",
                valor = 450F,
                medida = "mS|cm",
                icone = Icons.Default.Add
            )

            InformationBox(
                informacao = "Nível de água",
                valor = 7F,
                medida = "",
                icone = Icons.Default.Add
            )

            Button(
                onClick = {
                    navController.navigate("Graficos")
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
                    //TODO PESQUISA
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
                    //TODO HOME
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
                    //TODO Assistente vitual
                }
            ){
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Assisten vitural",
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
                        //TODO ABA DE NOTIFICAÇÕES
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Notificação",
                        tint = Color.Black
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
    informacao:String,
    valor: Float,
    medida: String,
    icone: ImageVector
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
            ),
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            //IMAGEM
            Icon(
                imageVector = icone,
                contentDescription = "Valor",
                tint = Color.Black,
                modifier = Modifier.size(14.dp)
            )

            //INFORMAÇÃO
            Column() {
                Text(
                    text = informacao,
                    fontSize = FontePadrao,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = medida,
                    fontSize = FontePadrao,
                    fontWeight = FontWeight.Bold
                )
            }

            //VALOR
            Box(){
                Text(
                    text = DecimalFormat(".##").format(valor),
                    fontSize = FonteGrande,
                    fontWeight = FontWeight.Bold
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
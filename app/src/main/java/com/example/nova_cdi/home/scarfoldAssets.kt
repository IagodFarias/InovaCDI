package com.example.nova_cdi.home

import FonteGrande
import TamanhoIcones
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nova_cdi.R
import com.example.nova_cdi.navigation.currentRoute
import com.example.nova_cdi.ui.theme.Green
import com.example.nova_cdi.ui.theme.NeutralBlue

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
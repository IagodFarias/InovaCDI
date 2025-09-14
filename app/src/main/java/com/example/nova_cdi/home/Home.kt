package com.example.nova_cdi.home

import BoxWidhtSize
import EspaçamentoLaterial
import FonteGrande
import FontePadrao
import TamanhoIcones
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import com.example.nova_cdi.Graficos.Data
import com.example.nova_cdi.Graficos.DataAll
import com.example.nova_cdi.Graficos.Mqtt
import com.example.nova_cdi.Graficos.Sample
import com.example.nova_cdi.Graficos.WifiInfo
import com.example.nova_cdi.Graficos.dataTypes
import com.example.nova_cdi.Graficos.handleSample
import com.example.nova_cdi.R
import com.example.nova_cdi.navigation.currentRoute
import com.example.nova_cdi.ui.theme.Blue
import com.example.nova_cdi.ui.theme.DarkBlue
import com.example.nova_cdi.ui.theme.Gray
import com.example.nova_cdi.ui.theme.Green
import com.example.nova_cdi.ui.theme.NeutralBlue
import com.google.gson.Gson
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import kotlinx.coroutines.delay
import java.text.DecimalFormat
import kotlin.collections.plus


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaHome(navController: NavController) {
    val context = LocalContext.current
    val client = remember { Mqtt(true) }
    val stats = remember{mutableStateOf("Procurando Rede...")}


    //Checando se está operando (Nota: Checar pela internet, checar pela conexão
    LaunchedEffect(Unit) {
        while(true){
            if(WifiInfo.isConnectedToWifi(context)) stats.value = "Operando"
            else stats.value = "Procurando Rede..."
            delay(1000)
        }
    }

    val actualData = remember { mutableStateOf(DataAll(
        x = 0.0F,
        condutividade = -1F,
        corrente = -1F,
        tensao = -1F,
        consumo = -1F
    )) }


    DisposableEffect(Unit) {
        client.get_last ({ publish ->
            val gson = Gson()

            val info = gson.fromJson(String(publish.payloadAsBytes), Sample::class.java)
            val newData = handleSample(info)

            if(newData != null){
                actualData.value = newData
            }

        })

        onDispose {
            client.disconnect()
        }
    }


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
                                text = stats.value,
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
                    type = dataTypes.CONSUMO,
                    informacao = "Consumo",
                    valor = actualData.value.consumo,
                    medida = "%",
                    icone = R.drawable.water,
                    Color(0xFF005AA0),
                    navController
                )


                InformationBox(
                    type = dataTypes.CORRENTE,
                    informacao = "Corrente",
                    valor = actualData.value.corrente,
                    medida = "mS|cm",
                    icone = R.drawable.spark,
                    Color(0xFFFEC420),
                    navController
                )

                InformationBox(
                    type = dataTypes.TENSAO,
                    informacao = "Tensão",
                    valor = actualData.value.tensao,
                    medida = "mS|cm",
                    icone = R.drawable.spark,
                    Color(0xFFFEC420),
                    navController
                )

                InformationBox(
                    type = dataTypes.CONDUTIVIDADE,
                    informacao = "Condutividade",
                    valor = actualData.value.condutividade,
                    medida = "mS|cm",
                    icone = R.drawable.spark,
                    Color(0xFFFEC420),
                    navController
                )



            }


        }


    }
}

fun toGraph(navController: NavController, context : Context, type : dataTypes){

    //TODO: MELHORAR A LOGICA PARA ENTRAR EM UM GRÁFICO, CHECAR SE ESTÁ CONECTADO A UMA CÉLULA
    if(WifiInfo.isConnectedToWifi(context)){
        when(type){
            dataTypes.CONDUTIVIDADE -> navController.navigate("Grafico_Condutividade")
            dataTypes.CONSUMO -> navController.navigate("Grafico_Consumo")
            dataTypes.TENSAO -> navController.navigate("Grafico_Tensao")
            dataTypes.CORRENTE -> navController.navigate("Grafico_Corrente")
        }
    }
}

@Composable
fun InformationBox(
    type : dataTypes,
    informacao: String,
    valor: Int,
    medida: String,
    icone: Int,
    color: Color,
    navController: NavController
){
    InformationBox(
        type,
         informacao,
        valor.toFloat(),
        medida,
        icone,
        color,
        navController
    )
}

@Composable
fun InformationBox(
    type : dataTypes,
    informacao: String,
    valor: Float,
    medida: String,
    icone: Int,
    color: Color,
    navController: NavController
){
    val context = LocalContext.current


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
            .padding(vertical = 15.dp)
            .clickable{
                toGraph(navController, context, type)
            }


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
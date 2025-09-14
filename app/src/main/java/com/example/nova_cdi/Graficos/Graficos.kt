package com.example.nova_cdi.Graficos

import FontePequena
import android.renderscript.Element
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nova_cdi.home.BottomBar
import com.example.nova_cdi.home.HomeTopBar
import com.example.nova_cdi.ui.theme.DarkBlue
import com.example.nova_cdi.ui.theme.Gray
import com.google.gson.Gson
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisGuidelineComponent
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.core.cartesian.Zoom
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider


@Composable
fun CondGraph(navController: NavController){
    TelaGrafico(navController, dataTypes.CONDUTIVIDADE)
}

@Composable
fun ConsGraph(navController: NavController){
    TelaGrafico(navController, dataTypes.CONSUMO)
}

@Composable
fun TenGraph(navController: NavController){
    TelaGrafico(navController, dataTypes.TENSAO)
}

@Composable
fun CorrGraph(navController: NavController){
    TelaGrafico(navController, dataTypes.CORRENTE)
}

@Composable
fun TelaGrafico(navController: NavController, type: dataTypes){

    val context = LocalContext.current
    val client = remember {Mqtt()}
    var dadoCaptado by remember { mutableStateOf("Testanto...") }



    var dataPontos by remember {
        mutableStateOf(
            listOf<Data>(
                
            )
        )
    }






    val dataChart = remember{ CartesianChartModelProducer() }

    //condutividade, consumo de energia, e os parâmetros de tensão e corrente nos eletrodos.

    LaunchedEffect(dataPontos) {

        dataChart.runTransaction {
            if(dataPontos.isNotEmpty()){
                val xValues = dataPontos.map {it.x}
                val yValues = dataPontos.map {it.y}

                lineSeries {
                    series(
                        x = xValues,
                        y = yValues
                    )
                }
            }
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
                .fillMaxSize()
                .padding(innerPadding)
        ){

            Spacer(modifier = Modifier.padding(20.dp))


            screenAssets(
                dataList = dataPontos,
                dotChart = dataChart,
                type = type
            )


            Box(){
                Column() {
                    Text(
                        text = "IP: ${WifiInfo.getIp()} \n Acesso: ${WifiInfo.isConnectedToWifi(context)} \n IPServer: ${WifiInfo.getServerIp()} "
                    )
                    Text(
                        text = "-----------------------"
                    )
                }
            }

            DisposableEffect(Unit) {


                client.get_last ({ publish ->

                    val gson = Gson()

                    val info = gson.fromJson(String(publish.payloadAsBytes), Sample::class.java)
                    val newData = handleSample(info, type)


                    if(newData != null){
                        if(newData.size > 1) {
                            dataPontos = newData
                        }else{
                            if(dataPontos.lastOrNull() == null || newData.last().x > dataPontos.last().x){
                                dataPontos = dataPontos + newData
                                dadoCaptado = "x: ${newData.last().x} \n${type.displayName}: ${newData.last().y}"
                            }
                        }


                    }
                })

                onDispose {
                    client.disconnect()
                }
            }


            Text(
                text = "Real: $dadoCaptado"
            )


        }
    }

}




@Composable
fun screenAssets(
    dataList : List<Data>,
    dotChart : CartesianChartModelProducer,
    type : dataTypes
){
    val modifierGraphBox = remember {
        Modifier
            .fillMaxWidth()
            .height(300.dp)
            .border(2.dp, Color.Black)
    }

    if(!dataList.isEmpty()) {
        Box(
            modifier = modifierGraphBox
                .padding(10.dp)
        ){
            Graph(dotChart,
                modifier = Modifier.fillMaxSize(),
                type.displayName
            )
        }

    }else{
        Box(
            modifier = modifierGraphBox
        )
    }


    Text(
        text = "Mostrando: ${type.displayName}"
    )
}

@Composable
fun Graph(
    pontos : CartesianChartModelProducer,
    modifier: Modifier,
    yTitle : String
){
    CartesianChartHost(
        modifier = modifier,
        modelProducer = pontos,
        chart = rememberCartesianChart(
            rememberLineCartesianLayer(
                rangeProvider = remember { CartesianLayerRangeProvider.fixed(
                    minX = minX.toDouble(),
                    maxX = maxX.toDouble()
                )
                }
            ),
            startAxis = VerticalAxis.rememberStart(
                title = yTitle,
                titleComponent = rememberTextComponent(
                    color = textColor,
                    textSize = textSize,
                ),
                itemPlacer = remember {
                    VerticalAxis.ItemPlacer.step(
                        step = { espaçamentoY.toDouble() }
                    )
                },
                guideline = if (showGrid) {
                    rememberAxisGuidelineComponent(
                        fill = fill(layersColors),
                        thickness = layerThicknes
                    )
                } else null,

            ),
            bottomAxis = HorizontalAxis.rememberBottom(
                title = nameX,
                titleComponent = rememberTextComponent(
                    color = textColor,
                    textSize = textSize
                ),
                itemPlacer = remember {
                    HorizontalAxis.ItemPlacer.aligned(
                        spacing = { 1 },
                        offset = { 0 }
                    )
                },
                guideline = if (showGrid) {
                    rememberAxisGuidelineComponent(
                        fill = fill(layersColors),
                        thickness = layerThicknes
                    )
                } else null
            ),
            getXStep = { espaçamentoX.toDouble() },

        )
        , zoomState = rememberVicoZoomState(
            zoomEnabled = zoom,
            initialZoom = remember { Zoom.min(Zoom.fixed(), Zoom.Content) }
        )

    )
}



@Preview(name = "Graficos")
@Composable
private fun PreviewChat() {
    val navController = rememberNavController()


    TelaGrafico(navController, dataTypes.CONDUTIVIDADE)
}





//CODIGOS ANTIGOS
/*
Usado em http

@Composable
fun newData(
    newSampleAppers: (Sample) -> Unit
) {
    val context = LocalContext.current
    var texto  by remember{ mutableStateOf("Buscando Dados...")}

    LaunchedEffect(Unit) {

        while(true){
            if( WifiInfo.isConnectedToWifi(context)){
                var call = httpsClient.GET(httpsClient.getUrl())


                call
                    .onSuccess {
                            jsonString ->
                            try{
                                val gson = Gson()
                                val infos = gson.fromJson(jsonString, Sample::class.java)

                                newSampleAppers(infos)

                                texto = "\n   x = ${infos.samples[0].x} , y = ${infos.samples[0].y}"


                            }catch(e : Exception){
                                texto = "Erro: $e"
                            }
                    }
                    .onFailure {
                            exception -> texto = "Código ${exception.message}"
                    }
            }else{
                texto = "Sem conexão..."
            }

            delay(1000L)
        }




    }


    Text(
        text = "Estado: $texto"
    )

}
*/

/*
RECEPÇÃO DE DADOS EM HTTP

newData(
    newSampleAppers = {
        newSample ->
            var newData = handleSample(newSample)
             s = "x"


            if(newData != null) {
                val lastOrNull = dataPontos.lastOrNull()?.x

                s = "x: ${newData.x} , y: ${newData.y} "


                if (lastOrNull == null || newData.x > lastOrNull) {
                    dataPontos = dataPontos + newData
                }

                if(dataPontos.size > 100) dataPontos = dataPontos.drop(1)

            }
    }
)

 */

/*
Escolha de gráfico
@Composable
fun chooseButtons(
    toCond : () -> Unit,
    toCons : () -> Unit,
    toTen : () -> Unit,
    toCor : () -> Unit
){
    val ButtonWidth = remember {80.dp}
    val ButtonHeight = remember {30.dp}

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly

    ){
        Button(
            modifier = Modifier
                .width(ButtonWidth)
                .height(ButtonHeight),

            shape = RoundedCornerShape(5.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = DarkBlue,
                contentColor = Gray
            ),

            onClick = {
                toCond()
            }
        ) {
            Text(
                text = "Condutividade",
                fontSize = FontePequena
            )

        }

        Button(
            modifier = Modifier
                .width(ButtonWidth)
                .height(ButtonHeight),

            shape = RoundedCornerShape(5.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Gray,
                contentColor = Color.Black
            ),


            onClick = {
                    toCons()
            }
        ){
            Text(
                text = "Consumo",
                fontSize = FontePequena
            )
        }

        Button(
            modifier = Modifier
                .width(ButtonWidth)
                .height(ButtonHeight),

            shape = RoundedCornerShape(5.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = DarkBlue,
                contentColor = Gray
            ),

            onClick = {
                toTen()
            }
        ) {
            Text(
                text = "Tensao",
                fontSize = FontePequena
            )

        }

        Button(
            modifier = Modifier
                .width(ButtonWidth)
                .height(ButtonHeight),

            shape = RoundedCornerShape(5.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Gray,
                contentColor = Color.Black
            ),


            onClick = {
                toCor()
            }
        ){
            Text(
                text = "Corrente",
                fontSize = FontePequena
            )
        }

    }
}
 */








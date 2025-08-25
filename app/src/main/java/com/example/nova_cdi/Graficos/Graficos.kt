
package com.example.nova_cdi.Graficos2

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.computeHorizontalBounds
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Typeface
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.VelocityTrackerCompat.clear
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nova_cdi.Graficos.*
import com.example.nova_cdi.home.BottomBar
import com.example.nova_cdi.home.HomeTopBar
import com.google.gson.Gson
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisGuidelineComponent
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import kotlin.random.Random
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.VicoTheme
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.core.cartesian.Zoom
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import kotlinx.coroutines.delay


@Composable
fun TelaGrafico(navController: NavController){

    val context = LocalContext.current

    var dataPontos by remember {
        mutableStateOf(
            listOf<Data>()
        )
    }

    val pontos = remember{ CartesianChartModelProducer() }

    LaunchedEffect(dataPontos) {
        pontos.runTransaction {
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

    val modifierGraphBox = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .border(2.dp, Color.Black)



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


            if(!dataPontos.isEmpty()) {
                Box(
                    modifier = modifierGraphBox
                        .padding(10.dp)
                ){
                    Graph(
                        pontos,
                        modifier = Modifier.fillMaxSize()
                    )
                }

            }else{
                Box(
                    modifier = modifierGraphBox
                )
            }


            var s by remember { mutableStateOf("Testanto...") }

            Box(){
                Column() {
                    Text(
                        text = "IP: ${WifiInfo.getIp()} \n Acesso: ${WifiInfo.isConnectedToWifi(context)} \n IPServer: ${WifiInfo.getServerIp()} "
                    )
                    Text(
                        text = "-----------------------"
                    )

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
                }
            }

            Text(
                text = "Real $s"
            )


        }
    }

}


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


@Composable
fun Graph(
    pontos : CartesianChartModelProducer,
    modifier: Modifier
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
                title = nameY,
                titleComponent = rememberTextComponent(
                    color = textColor,
                    textSize = textSize
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
            zoomEnabled = false,
            initialZoom = remember { Zoom.min(Zoom.fixed(), Zoom.Content) }
        )

    )
}



@Preview(name = "Graficos")
@Composable
private fun PreviewChat() {
    val navController = rememberNavController()


    TelaGrafico(navController)
}











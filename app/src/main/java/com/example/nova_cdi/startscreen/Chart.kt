package com.example.nova_cdi.chart

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun TelaComGrafico(navController: NavController) {
    val modelProducer = remember { CartesianChartModelProducer() }
    val dataPoints = remember { mutableStateListOf<Float>() }

    LaunchedEffect(Unit) {
        val client = OkHttpClient()
        while (true) {
            try {
                val body = withContext(Dispatchers.IO) {
                    client.newCall(
                        Request.Builder()
                            .url("http://192.168.0.2/data")
                            .build()
                    ).execute().body?.string()
                }
                body?.let {
                    val ax = JSONObject(it).getJSONObject("accelerometer").getInt("x").toFloat()
                    Log.d("MPU6050", "Leu ax = $ax")
                    dataPoints.add(ax)
                    if (dataPoints.size > 50) dataPoints.removeAt(0)
                    modelProducer.runTransaction {
                        lineSeries { series(dataPoints) }
                    }
                }
            } catch (e: Exception) {
                Log.e("MPU6050", "Erro na leitura HTTP", e)
            }
            delay(500)
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Button(
                onClick = { navController.navigate("home") },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text("Home")
            }

            Text(
                text = "Corrente X Tempo",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                CartesianChartHost(
                    chart = rememberCartesianChart(
                        rememberLineCartesianLayer(),
                        startAxis = VerticalAxis.rememberStart(),
                        bottomAxis = HorizontalAxis.rememberBottom(),
                    ),
                    modelProducer = modelProducer,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

    }
}

@Preview
@Composable
private fun Tela() {
    val navController = rememberNavController()
    TelaComGrafico(navController)
}



//Melhorias que você pode fazer:
//Receber o IP/dominio via parâmetro para não deixar fixo no código.
//
//Criar uma variável ou val para o endereço, facilitando alteração.
//
//Criar uma tela para o usuário informar o IP e usar essa info na requisição.
//
//Tratar erros quando o IP estiver errado ou o servidor não responder.









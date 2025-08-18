package com.example.nova_cdi.startscreen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
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

@Composable
fun TelaComGrafico(navController: NavHostController, ipAddress: String) {
    val modelProducer = remember { CartesianChartModelProducer() }
    val dataPoints = remember { mutableStateListOf<Float>() }

    LaunchedEffect(ipAddress) {
        val client = OkHttpClient()
        while (true) {
            try {
                val body = withContext(Dispatchers.IO) {
                    client.newCall(
                        Request.Builder()
                            .url("http://$ipAddress/data")
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
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text("Voltar")
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

@Composable
fun InputScreen(onIpEntered: (String) -> Unit) {
    var ipAddress by remember { mutableStateOf("") }

    Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text("Informe o endereço IP do servidor:")
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = ipAddress,
                onValueChange = { ipAddress = it },
                label = { Text("Endereço IP") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (ipAddress.isNotBlank()) {
                        onIpEntered(ipAddress)
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Continuar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun inputsreempreview() {
    InputScreen(onIpEntered = { /* no-op for preview */ })
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    var ipAddress by rememberSaveable { mutableStateOf<String?>(null) }

    NavHost(
        navController = navController,
        startDestination = if (ipAddress == null) "input" else "grafico"
    ) {
        composable("input") {
            InputScreen(onIpEntered = { ip ->
                ipAddress = ip
                navController.navigate("grafico") {
                    popUpTo("input") { inclusive = true }
                }
            })
        }
        composable("grafico") {
            ipAddress?.let {
                TelaComGrafico(navController, it)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaComGraficoPreview() {
    val navController = rememberNavController()
    TelaComGrafico(navController = navController, ipAddress = "192.168.0.1")
}
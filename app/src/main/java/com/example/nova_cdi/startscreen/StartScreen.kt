package com.example.nova_cdi.startscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.nova_cdi.R
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

data class SensorCardData(val label: String, val value: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InovaCDIScreen(navController: NavHostController, ipAddress: String) {
    val sensorData = remember {
        listOf(
            SensorCardData("Nível de Água", "70%"),
            SensorCardData("Condutividade mS|cm", "450"),
            SensorCardData("pH", "7"),
            SensorCardData("Nível de Água", "70%"),
            SensorCardData("Nível de Água", "70%")
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "InovaCDI",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = "Notificações",
                            tint = Color.Black
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Perfil",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomNavigationBar()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Button(
                onClick = {},
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEEF3FF))
            ) {
                Icon(
                    imageVector = Icons.Filled.Dashboard,
                    contentDescription = "Painel",
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Operando", color = Color.Black)
            }

            LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                items(sensorData) { data ->
                    SensorCard(data = data) {
                        navController.navigate("chart/$ipAddress")
                    }
                }
            }


        }
    }
}

@Composable
fun SensorCard(data: SensorCardData, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_placeholder),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(data.label, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Text(data.value, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
    }
}

@Composable
fun BottomNavigationBar() {
    NavigationBar(
        containerColor = Color(0xFF009CA6),
        contentColor = Color.White
    ) {
        NavigationBarItem(
            selected = true,
            onClick = { },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home",
                    tint = Color.White
                )
            },
            label = { Text("") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Buscar",
                    tint = Color.White
                )
            },
            label = { Text("") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Adicionar",
                    tint = Color.White
                )
            },
            label = { Text("") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Perfil",
                    tint = Color.White
                )
            },
            label = { Text("") }
        )
    }
}





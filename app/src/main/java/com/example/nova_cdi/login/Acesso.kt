package com.example.nova_cdi.login

import FonteGrande
import FontePadrao
import EspaçamentoLaterial
import FontePequena
import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.nova_cdi.ui.theme.DarkBlue
import com.example.nova_cdi.ui.theme.Gray
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import java.nio.file.WatchEvent


@Composable
fun AcessoTexto(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.padding(EspaçamentoLaterial)
    ) {

        Box() {
            Text(
                text = "Acesse",
                fontSize = FonteGrande,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.padding(5.dp))

        Box(){
            Text(
                text = "Com e-mail e senha para entrar",
                fontSize = FontePadrao
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaAcesso(navController: NavController){
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.safeDrawing.asPaddingValues()),
        topBar = {
            Column {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { //TODO: FUNÇÃO DE VOLTAR E MELHORAR IMAGEM
                            navController.popBackStack()
                        }) {
                            Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Menu")
                        }
                    },
                    title = {}
                )
                AcessoTexto()
            }

        },



        ) { innerPadding ->

        // Conteúdo principal da tela
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(EspaçamentoLaterial)
            //.fillMaxSize()
            ,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(modifier = Modifier.padding(8.dp))

            var email by remember { mutableStateOf("") }
            var senha by remember { mutableStateOf("") }


            BlocoCadastro(
                texto = "E-mail",
                inBoxTexto = "Digite seu e-mail",
                resposta = email,
                respostaChange = {email = it}
            )


            BlocoCadastroSenha(
                texto = "Crie uma senha",
                inBoxTexto = "Digite sua senha",
                resposta = senha,
                respostaChange = {senha = it}
            )



            LembrarMe(navController)


            FlowButtons(navController)





            //TODO: FAZER CHECAGEM E ENCRIPTAR VALORES

            Text(
                text = "Email: $email\nSenha: $senha"
            )

        }
    }
}


@Composable
fun FlowButtons(navController: NavController){
    val ButtonWidth = 170.dp
    val ButtonHeight = 50.dp

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
                navController.navigate("Home")
            }
        ) {
            Text(
                text = "Acessar",
                fontSize = FontePadrao
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
                navController.navigate("Cadastro")
            }
        ){
            Text(
                text = "Cadastrar",
                fontSize = FontePadrao
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LembrarMe(navController: NavController){
    var lembrar = remember { mutableStateOf(false) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ){

        Box(
            modifier = Modifier
                .width(15.dp)
                .height(15.dp)
                .clickable{lembrar.value = !lembrar.value}
                .background(
                    color = Gray,
                    shape = RoundedCornerShape(2.dp)
                )
                .border(
                    width = 0.1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(2.dp)
                ),
            contentAlignment = Alignment.Center,

        ){
            if (lembrar.value) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Lembrar-me",
                    tint = Color.Black,
                    modifier = Modifier.size(14.dp)
                )
            }
        }

        Text(
            text = "Lembrar minha senha",
            fontSize = FontePequena
            
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ){
            Box(
                Modifier.clickable(
                    onClick = {
                        //TODO FAZER O CAMINHO PARA RECUPERAR A SENHA
                    }
                )
            ){
                Text(
                    text = "Esqueci minha senha",
                    fontSize = FontePequena
                )
            }
        }

    }


    //TODO: ADICIONAR O QUE FAZER QUANDO O LEMBRAR FOR ATIVADO


}

@Preview(name = "Acesso")
@Composable
private fun PreviewTelaAcesso() {
    val navController = rememberNavController()

    TelaAcesso(navController)
}

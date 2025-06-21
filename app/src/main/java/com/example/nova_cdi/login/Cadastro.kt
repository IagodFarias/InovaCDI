package com.example.nova_cdi.login


import EspaçamentoLaterial
import FonteGrande
import FontePadrao
import FontePequena
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.nova_cdi.ui.theme.DarkBlue
import com.example.nova_cdi.ui.theme.Gray
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController



@Composable
fun CadastroTexto() {

    Column(
        modifier = Modifier.padding(EspaçamentoLaterial)
    ) {

        Box() {
            Text(
                text = "Cadastre-se",
                fontSize = FonteGrande,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.padding(5.dp))

        Box(){
            Text(
                text = "Informe seu e-mail e crie uma senha.",
                fontSize = FontePadrao
            )
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCadastro(navController: NavController){
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
                CadastroTexto()
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

            var email by remember {mutableStateOf("")}
            var emailCheck by remember {mutableStateOf("")}
            var senha by remember {mutableStateOf("")}
            var senhaCheck by remember {mutableStateOf("")}


            BlocoCadastro(
                texto = "E-mail",
                inBoxTexto = "Digite seu e-mail",
                resposta = email,
                respostaChange = {email = it}
            )

            BlocoCadastro(
                texto = "Repita o e-mail",
                inBoxTexto = "Digite seu e-mail",
                resposta = emailCheck,
                respostaChange = {emailCheck = it}
            )

            BlocoCadastroSenha(
                texto = "Crie uma senha",
                inBoxTexto = "Digite sua senha",
                resposta = senha,
                respostaChange = {senha = it}
            )

            BlocoCadastroSenha(
                texto = "Repita sua senha",
                inBoxTexto = "Repita sua senha",
                resposta = senhaCheck,
                respostaChange = {senhaCheck = it}
            )

            

            //TODO: FAZER CHECAGEM E ENCRIPTAR VALORES
            Text(
                text = "Email: $email\nCheck: $emailCheck \nSenha: $senha \nCheck: $senhaCheck"
            )

        }
    }


}


@Composable
fun BlocoCadastro(
    texto:String,
    inBoxTexto:String = "",
    resposta: String,
    respostaChange: (String) -> Unit
){
    Column(
    ){
        Text(
            text = texto,
            fontSize = FontePequena
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),

            value = resposta,
            onValueChange = {respostaChange(it)},
            label = { Text(
                text = inBoxTexto,
                fontSize = FontePadrao
            ) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Gray,      // fundo quando focado
                unfocusedContainerColor = Gray,    // fundo quando sem foco
                disabledContainerColor = Gray        // fundo se estiver desativado
            )
        )
    }

}

@Composable
fun BlocoCadastroSenha(
    texto:String,
    inBoxTexto:String = "",
    resposta: String,
    respostaChange: (String) -> Unit
){
    var escondido = remember { mutableStateOf(true) }

    Column(){
        Text(
            text = texto,
            fontSize = FontePequena
        )


        TextField(
            modifier = Modifier.fillMaxWidth(),

            value = resposta,
            onValueChange = {respostaChange(it)},
            label = { Text(
                text = inBoxTexto,
                fontSize = FontePadrao
            ) },

            colors = TextFieldDefaults.colors(
                focusedContainerColor = Gray,      // fundo quando focado
                unfocusedContainerColor = Gray,    // fundo quando sem foco
                disabledContainerColor = Gray        // fundo se estiver desativado
            ),
            visualTransformation = if(escondido.value) PasswordVisualTransformation() else VisualTransformation.None,

            trailingIcon = {
                TextButton(onClick = {escondido.value = !escondido.value}) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Ver",
                        tint = DarkBlue,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }


        )


    }

}





@Preview(name = "Scarfold")
@Composable
private fun PreviewLaterais() {
    val navController = rememberNavController()

    TelaCadastro(navController)
}
package com.example.nova_cdi.home.Chat

import BoxWidhtSize
import EspaçamentoLaterial
import FonteGrande
import FontePadrao
import TamanhoIcones
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nova_cdi.Graficos.WifiInfo
import com.example.nova_cdi.home.BottomBar
import com.example.nova_cdi.home.HomeTopBar
import com.example.nova_cdi.home.InformationBox
import com.example.nova_cdi.home.TelaHome
import com.example.nova_cdi.login.AcessoTexto
import com.example.nova_cdi.ui.theme.Blue
import com.example.nova_cdi.ui.theme.DarkBlue
import com.example.nova_cdi.ui.theme.Gray
import com.example.nova_cdi.ui.theme.LeafGreen
import com.example.nova_cdi.ui.theme.NeutralBlue
import com.example.nova_cdi.ui.theme.WhiteLeafGreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.nova_cdi.home.ChatBot.ChatBot
import kotlinx.coroutines.launch
import android.util.Log


data class Message(val text: String, val isSentByUser: Boolean)


@Composable
fun TelaChat(navController: NavController) {

    //Teste
    val bot = remember {
        ChatBot(
            knowledgeContext = teste
        )
    }

    var messages by remember {
        mutableStateOf(
            listOf(
                Message("Olá! Como posso ajudar?", isSentByUser = false)
            )
        )
    }
    // Texto que o usuário está digitando
    var textInput by remember { mutableStateOf("") }
    // Estado para controlar a LazyColumn
    val lazyListState = rememberLazyListState()
    // Scope de coroutine para rolar a lista
    val coroutineScope = rememberCoroutineScope()


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.safeDrawing.asPaddingValues()),

        topBar = {
            GenericTopBar(
                navController,
                "Suporte"
            )
        },
        bottomBar = {
            Column() {
                MessageInput(
                    text = textInput,
                    onTextChanged = { textInput = it },
                    onSendClicked = {
                        if (textInput.isNotBlank()) {

                            val userMessageText = textInput
                            // Adiciona a mensagem do usuário à lista
                            messages = messages + Message(userMessageText, isSentByUser = true)
                            textInput = "" // Limpa o campo

                            // Lança uma coroutine para chamar o bot sem travar a tela
                            coroutineScope.launch {

                                val botResponse = bot.getResponse(userMessageText)
                                // Adiciona a resposta do bot à lista
                                messages = messages + Message(botResponse, isSentByUser = false)
                            }

                        }
                    }
                )
                BottomBar(navController)
            }
        }

    ){
        paddingValues ->
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(messages) { message ->
                MessageBubble(message = message)
            }
        }

        LaunchedEffect(messages) {
            // Rola suavemente para o último item da lista
            if (messages.isNotEmpty()) {
                lazyListState.animateScrollToItem(index = messages.size - 1)
            }
        }

    }
}

@Composable
fun MessageBubble(message: Message) {
    // Definimos o alinhamento que será usado pelo Column.
    // Alignment.Start alinha à esquerda, Alignment.End alinha à direita.
    val alignment = if (message.isSentByUser) Alignment.End else Alignment.Start

    // As cores continuam as mesmas
    val bubbleColor = if (message.isSentByUser) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.secondaryContainer
    }

    // 1. Usamos um Column que preenche toda a largura.
    //    A "mágica" está em definir o `horizontalAlignment` aqui.
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = alignment // <-- A CORREÇÃO PRINCIPAL ESTÁ AQUI
    ) {
        // 2. O conteúdo (a bolha do chat) agora é um filho direto do Column
        //    e será alinhado de acordo com a regra que definimos acima.
        Box(
            modifier = Modifier
                // A bolha continua ocupando no máximo 80% da largura para não ficar colada na borda oposta
                .fillMaxWidth(0.8f)
                .clip(RoundedCornerShape(16.dp))
                .background(bubbleColor)
                .padding(12.dp)
        ) {
            Text(
                text = message.text,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}


@Composable
fun MessageInput(
    text: String,
    onTextChanged: (String) -> Unit,
    onSendClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChanged,
            modifier = Modifier.weight(1f),
            placeholder = { Text("Digite algo...") },
            shape = RoundedCornerShape(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            onClick = onSendClicked,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Enviar",
                tint = Color.White
            )
        }
    }
}


@Composable
fun GenericTopBar(
    navController: NavController,
    text : String
){
    Box(
        modifier = Modifier
            .width(BoxWidhtSize)
            .background(
                color = WhiteLeafGreen,
            )


    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft, //TODO: MELHORAR CIONE
                    tint = LeafGreen,
                    modifier = Modifier
                        .width(TamanhoIcones.times(5))
                        .height(TamanhoIcones.times(5)),
                    contentDescription = "(+)"
                )
            }

            Text(
                text = text,
                fontSize = FontePadrao,
                fontWeight = FontWeight.Bold,
                color = NeutralBlue
            )

            Box(
                modifier = Modifier
                    .width(TamanhoIcones.times(2))
                    .height(TamanhoIcones.times(2))
            )
        }
    }
}


@Preview(name = "Chat")
@Composable
private fun PreviewChat() {
    val navController = rememberNavController()

    TelaChat(navController)
}
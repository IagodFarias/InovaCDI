package com.example.nova_cdi.home.ChatBot

import android.util.Log
import com.example.nova_cdi.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GoogleGenerativeAIException
import com.google.ai.client.generativeai.type.PromptBlockedException
import com.google.ai.client.generativeai.type.ServerException
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.CancellationException
import java.net.UnknownHostException

// É uma boa prática definir a configuração do modelo aqui
private val geminiConfig = generationConfig {
    temperature = 0.3f
}

class ChatBot(
    private val knowledgeContext: String,
    private val apiKey: String = BuildConfig.API_KEY
) {

    // --- CORREÇÃO AQUI ---
    // Defina a TAG de Log como uma constante da classe.
    companion object {
        private const val TAG = "ChatBot"
    }
    // ----------------------

    private val model = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = apiKey,
        generationConfig = geminiConfig
    )

    suspend fun getResponse(userInput: String): String {

        try {
            val finalPrompt = """
            Você é um assistente prestativo. Use estritamente o contexto abaixo para responder.
            Não use nenhum conhecimento externo. Se a resposta não estiver no contexto,
            diga "Desculpe, só posso responder sobre os tópicos que conheço.".

            


            ---
            Contexto:
            $knowledgeContext
            ---

            Pergunta do Usuário:
            "$userInput"

            Resposta:
            """.trimIndent()


            val response = model.generateContent(finalPrompt)
            return response.text ?: "Não consegui gerar uma resposta."

        } catch (e: Exception) {
            val TAG = "ChatBot" // Garante que a TAG está aqui

            val userFriendlyMessage = when (e) {

                // --- CASO MODIFICADO PARA SER MAIS INTELIGENTE ---
                is GoogleGenerativeAIException -> {
                    // Verificamos a "causa" interna do erro.
                    if (e.cause is kotlinx.serialization.MissingFieldException) {
                        // Se a causa for o erro de serialização, sabemos que é o 503 com formato inesperado.
                        Log.e(TAG, "Erro 503 (Sobrecarga) com formato de resposta inesperado.", e)
                        "O serviço está sobrecarregado. Por favor, tente novamente mais tarde."
                    } else {
                        // Se não, é outro erro genérico da API Gemini.
                        Log.e(TAG, "Erro na API Gemini: ${e.message}", e)
                        "Erro da API: O serviço retornou um erro. Verifique o Logcat."
                    }
                }

                // Mantemos os outros casos como uma rede de segurança
                is PromptBlockedException -> {
                    Log.w(TAG, "Prompt bloqueado por segurança.", e)
                    "Sua pergunta foi bloqueada por motivos de segurança."
                }
                is UnknownHostException -> {
                    Log.e(TAG, "Não foi possível conectar ao host.", e)
                    "Sem conexão com a internet."
                }
                is CancellationException -> {
                    Log.i(TAG, "A chamada foi cancelada.", e)
                    throw e
                }
                else -> {
                    Log.e(TAG, "Um erro inesperado e não identificado ocorreu.", e)
                    "Erro Desconhecido: ${e.javaClass.simpleName} - ${e.message}"
                }
            }
            return userFriendlyMessage
        }
    }
}
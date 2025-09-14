package com.example.nova_cdi.Graficos

import androidx.compose.runtime.remember
import com.google.gson.annotations.SerializedName
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import kotlin.Float
import kotlin.math.roundToInt


enum class dataTypes(val displayName : String){
    CONDUTIVIDADE("Condutividade"),
    CONSUMO("Consumo"),
    TENSAO("Tensão"),
    CORRENTE("Corrente")
}

data class JsonData (@SerializedName("time") val x:Int,
                     @SerializedName("condutividade") val condutividade : Float,
                     @SerializedName("consumo") val consumo : Float,
                     @SerializedName("tensao") val tensao : Float,
                     @SerializedName("corrente") val corrente : Float)
data class Sample(@SerializedName("samples") val samples : List<JsonData>)

data class Data(val x:Float, val y:Float)

data class DataAll(
    val x:Float,
    val condutividade : Float,
    val consumo : Float,
    val tensao : Float,
    val corrente : Float
)



fun handleSample(
    data : Sample,
    type : dataTypes
) : List<Data> ?
{
    var novosPontos : MutableList<Data> = mutableListOf()

    if(data.samples.isEmpty()) return null

    for(s in data.samples){
        val horas = s.x.toFloat() / 3600f

        val horasArredondadas = (horas * 10000).roundToInt() / 10000.0f

        val yValue: Float = when(type){
            dataTypes.CONDUTIVIDADE -> s.condutividade
            dataTypes.CONSUMO -> s.consumo
            dataTypes.TENSAO -> s.tensao
            dataTypes.CORRENTE -> s.corrente
        }

        val novoPonto = Data(
            x = horasArredondadas,
            y = yValue
        )

        novosPontos.add(novoPonto)
    }


    val list : List<Data> ? = novosPontos //.sortedBy{it.x} Creio que não vai precisar, mas se tiver dando erro, colocar de volta

    return list
}

fun handleSample(
    data : Sample)
: DataAll ?
{

    if(data.samples.isEmpty()) return null

    val pontoOriginal = data.samples[0]

    val horas = pontoOriginal.x.toFloat() / 3600f

    val horasArredondadas = (horas * 10000).roundToInt() / 10000.0f



    val novoPonto = DataAll(
        x = horasArredondadas,
        corrente = data.samples[0].corrente,
        condutividade = data.samples[0].condutividade,
        consumo = data.samples[0].consumo,
        tensao = data.samples[0].tensao
    )

    return novoPonto
}
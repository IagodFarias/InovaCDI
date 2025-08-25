package com.example.nova_cdi.Graficos

import com.google.gson.annotations.SerializedName
import kotlin.Float
import kotlin.math.roundToInt


data class JsonData (@SerializedName("time") val x:Int, @SerializedName("data") val y : Int)
data class Sample(@SerializedName("samples") val samples : List<JsonData>)

data class Data(val x:Float, val y: Int)


fun handleSample(
    data : Sample
) : Data ?
{

    if(data.samples.isEmpty()) return null

    val pontoOriginal = data.samples[0]

    val horas = pontoOriginal.x.toFloat() / 3600f

    val horasArredondadas = (horas * 10000).roundToInt() / 10000.0f

    val novoPonto = Data(
        x = horasArredondadas,
        y = data.samples[0].y
    )

    return novoPonto
}
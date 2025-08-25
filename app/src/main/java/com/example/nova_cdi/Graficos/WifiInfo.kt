package com.example.nova_cdi.Graficos

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.net.wifi.WifiManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import java.net.InetAddress
import java.net.UnknownHostException

object WifiInfo{

    var IP : String? = null
            private set

    private fun setIP(context : Context){
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val ipInt = wifiManager.connectionInfo.ipAddress

        // O ipAddress está como Int, precisamos converter para formato String legível
        val ipString = try {
            val bytes = byteArrayOf(
                (ipInt and 0xff).toByte(),
                (ipInt shr 8 and 0xff).toByte(),
                (ipInt shr 16 and 0xff).toByte(),
                (ipInt shr 24 and 0xff).toByte()
            )
            InetAddress.getByAddress(bytes).hostAddress
        } catch (e: UnknownHostException) {
            null
        }

        this.IP = ipString.toString()
    }

    fun getIp() : String? {
        if(this.IP != "") return this.IP
        else return null
    }

    fun getServerIp() : String?{
        var serverIpParts = this.IP?.split(".")?.toMutableList()

        serverIpParts?.set(3, "200")

        var serverIP = serverIpParts?.joinToString(".")


        return serverIP
    }

    fun isConnectedToWifi(context: Context): Boolean {
        val connectivityManager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        val isWifi = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)

        if (isWifi) {
            setIP(context)
        } else {
            IP = null
        }

        return isWifi
    }
}


object httpsClient{
    val client = OkHttpClient()

    fun getUrl(all : Boolean = false) : String{
        var url : String


        url = "http://${WifiInfo.getServerIp()}/data"

        if(!all) url = "$url/last"



        return url
    }


    suspend fun GET(url: String): Result<String> {

        return withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .url(url)
                .build()

            try{
                val call = client.newCall(request).execute()

                call.use {
                    if(call.isSuccessful){
                        Result.success(it.body?.string() ?: "")
                    }else{
                        Result.failure(IOException("Erro na requisição: ${it.code}"))
                    }
                }

            } catch(e: IOException){
                Result.failure(e)
            }
        }
    }
}
package com.example.nova_cdi.Graficos

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.net.wifi.WifiManager
import java.net.InetAddress
import java.net.UnknownHostException

class WifiInfo (private val context: Context){
    var IP : String = ""

    private fun setIP(){
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
        var serverIpParts = this.IP.split(".").toMutableList()

        serverIpParts[3] = "200"

        var serverIP = serverIpParts.joinToString(".")


        return serverIP
    }

    fun isConnectedWifi(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(network) ?: return false
            val isWifi = actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)

            if(isWifi) this.setIP()

            return actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            val isWifi = networkInfo.type == ConnectivityManager.TYPE_WIFI && networkInfo.isConnected

            if (isWifi) this.setIP()

            return isWifi
        }

    }



}
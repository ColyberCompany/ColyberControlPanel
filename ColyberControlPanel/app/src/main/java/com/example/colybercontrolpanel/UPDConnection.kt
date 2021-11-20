package com.example.colybercontrolpanel

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.net.*

private const val LogTag = "UDPConn"

object UDPConn {
    private val datagramSocket = DatagramSocket(null)
    private lateinit var droneIpAddress: InetAddress
    private const val MaxBufferLen = 100

    var connState = false
        private set

    var newDataReceivedCallback: (() -> Unit)? = null

    /**
     * callback - function that receives connection result and is called when connecting is finished
     */
    fun connectAsync(callback: (Boolean) -> Unit)
    {
        if (!connState)
            UDPConnectAsyncTask(callback).execute()
    }


    private class UDPConnectAsyncTask (callback: (Boolean) -> Unit) : AsyncTask<Void, Void, Void>() {
        var connectingResult = false
        private val connectingCallback = callback

        override fun doInBackground(vararg p0: Void?): Void? {
            try {
                datagramSocket.reuseAddress = true
                datagramSocket.broadcast = true
                datagramSocket.bind(InetSocketAddress(Globals.DronePort))
            } catch (e: SocketException) {
                e.printStackTrace()
                Log.e(LogTag, e.message.toString())
            }

            droneIpAddress = InetAddress.getByName(Globals.DroneIPAddress)

            connectingResult = true
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            connectingCallback(connectingResult)
            Log.e(LogTag, "onPostExecute of UDPConnectAsyncTask. Result: $connectingResult")

            if (connectingResult) {
                connState = true
                Thread(receivingUpdateRunnable).start()
            }
        }
    }


    private val receivingUpdateRunnable = Runnable {
        while (true)
        {
            val receiveData = ByteArray(MaxBufferLen)
            val receiveDP = DatagramPacket(receiveData, receiveData.size)

            try {
                datagramSocket.receive(receiveDP)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            // TODO: handle received data (update global variables)
            Log.e(LogTag, "Received ${receiveDP.length} bytes!")

            // values to be updated are in Globals object

            newDataReceivedCallback?.invoke()
        }
    }

}

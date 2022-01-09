package com.example.colybercontrolpanel

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.net.*
import java.nio.ByteBuffer

private const val LogTag = "UDPConn"

object UDPConn {
    private val datagramSocket = DatagramSocket(null)
    private lateinit var droneIpAddress: InetAddress
    private const val MaxBufferLen = 100
    private const val DroneDataPacketID: Byte = 69

    var connState = false
        private set

    var newDataReceivedCallback: (() -> Unit)? = null

    /**
     * callback - function that receives connecting result and is called when connecting is finished
     */
    fun connectAsync(callback: (Boolean) -> Unit)
    {
        if (!connState)
            UDPConnectAsyncTask(callback).execute()
        else
            callback(true)
    }

    fun sendPID() {
        // TODO: implement
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

            if (receiveDP.length == 62 && receiveData[0] == DroneDataPacketID) { // TODO: ID is on first two bytes, not only on index 0
                Globals.DroneData.angleX = unpackFloatFromBuffer(receiveData, 2)
                Globals.DroneData.angleY = unpackFloatFromBuffer(receiveData, 6)
                Globals.DroneData.angleZ = unpackFloatFromBuffer(receiveData, 10)
                Globals.DroneData.altitude = unpackFloatFromBuffer(receiveData, 14)
                Globals.DroneData.latitude = unpackFloatFromBuffer(receiveData, 18)
                Globals.DroneData.longitude = unpackFloatFromBuffer(receiveData, 22)
                Globals.DroneData.accX = unpackFloatFromBuffer(receiveData, 26)
                Globals.DroneData.accY = unpackFloatFromBuffer(receiveData, 30)
                Globals.DroneData.accZ = unpackFloatFromBuffer(receiveData, 34)
                Globals.DroneData.gyroX = unpackFloatFromBuffer(receiveData, 38)
                Globals.DroneData.gyroY = unpackFloatFromBuffer(receiveData, 42)
                Globals.DroneData.gyroZ = unpackFloatFromBuffer(receiveData, 46)
                Globals.DroneData.magnX = unpackFloatFromBuffer(receiveData, 50)
                Globals.DroneData.magnY = unpackFloatFromBuffer(receiveData, 54)
                Globals.DroneData.magnZ = unpackFloatFromBuffer(receiveData, 58)

                // trigger callback
                newDataReceivedCallback?.invoke()
            }
        }
    }

    private fun bytesToFloat(byte1: Byte, byte2: Byte, byte3: Byte, byte4: Byte): Float {
        val bytes = byteArrayOf(byte1, byte2, byte3, byte4)
        val buffer = ByteBuffer.wrap(bytes)
        return buffer.float
    }


    private fun unpackFloatFromBuffer(buffer: ByteArray, floatFirstIndex: Int): Float {
        return bytesToFloat(buffer[floatFirstIndex + 3],
            buffer[floatFirstIndex + 2],
            buffer[floatFirstIndex + 1],
            buffer[floatFirstIndex])
    }

}

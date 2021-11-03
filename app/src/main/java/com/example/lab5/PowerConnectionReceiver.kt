package com.example.lab5

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.provider.Settings.Global.getString
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService

class PowerConnectionReceiver : BroadcastReceiver() {

    private var context : Context? = null
    private lateinit var channel: NotificationChannel
    private val BATTERY_CHANNEL_ID = "BATTERY_CHANNEL_ID"
    private var isChannelCreated = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        this.context = context
        val action = intent?.action

        when(action){
            Intent.ACTION_POWER_CONNECTED -> {
                notifyUser()
            }
            Intent.ACTION_POWER_DISCONNECTED -> {

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun notifyUser(){
        if(!isChannelCreated){
            createChannel();
        }
        val mBuilder = NotificationCompat.Builder(context!!, BATTERY_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Battery Notification")
            .setContentText("Battery is charging")

        val notification = mBuilder.build()
        val notificationManagerCompat = NotificationManagerCompat.from(context!!)
        notificationManagerCompat.notify(1, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(){
        channel = NotificationChannel(BATTERY_CHANNEL_ID, "BATTERY_NOTIFICATIONS_CHANNEL", NotificationManager.IMPORTANCE_DEFAULT)
        channel!!.description = "A channel for battery notifications"
        channel!!.lightColor = Color.GREEN;
        val notificationManager = getSystemService(context!!, NotificationManager::class.java)
        notificationManager!!.createNotificationChannel(channel!!)
        isChannelCreated = true
    }
}
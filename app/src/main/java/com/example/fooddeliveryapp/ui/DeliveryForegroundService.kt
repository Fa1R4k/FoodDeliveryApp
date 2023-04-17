package com.example.fooddeliveryapp.ui

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.fooddeliveryapp.R

class DeliveryForegroundService() : Service() {

    private var countDownTimer: CountDownTimer? = null
    private val notificationManager: NotificationManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
    private val notificationBuilder: NotificationCompat.Builder by lazy { buildNotification() }

    override fun onCreate() {
        super.onCreate()
        startTimer()
        startForeground(NOTIFICATION_SERVICE_ID, notificationBuilder.build())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_REMOVE)
        }
        countDownTimer?.cancel()
        countDownTimer = null
    }

    private fun buildNotification(): NotificationCompat.Builder {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_SERVICE_CHANNEL_ID,
                NOTIFICATION_SERVICE_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        return NotificationCompat.Builder(this, NOTIFICATION_SERVICE_CHANNEL_ID)
            .setContentTitle(applicationContext.getString(R.string.delivery))
            .setContentText(
                applicationContext.getString(
                    R.string.notification_text, getTimeDurationText(
                        TIME_DURATION
                    )
                )
            )
            .setOnlyAlertOnce(true)
            .setSmallIcon(R.mipmap.ic_launcher)
    }

    private fun getTimeDurationText(timeDurationMillis: Long): String {
        val minutes = ((timeDurationMillis / (1000 * 60) + 1)).toInt()
        return "$minutes"
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(TIME_DURATION, INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {

                val notificationText =
                    applicationContext.getString(
                        R.string.notification_text, getTimeDurationText(
                            millisUntilFinished
                        )
                    )
                updateNotificationText(notificationText)
            }


            private fun updateNotificationText(notificationText: String) {
                notificationBuilder.setContentText(notificationText)
                notificationManager.notify(NOTIFICATION_SERVICE_ID, notificationBuilder.build())
            }

            override fun onFinish() {
                val notificationText = getString(R.string.delivery_success)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        NOTIFICATION_CHANNEL_ID,
                        NOTIFICATION_NAME,
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    val notificationManager =
                        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.createNotificationChannel(channel)
                }

                val notificationBuilder =
                    NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_app)
                        .setContentTitle(applicationContext.getString(R.string.delivery))
                        .setContentText(notificationText)
                val notificationManager =
                    getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    stopForeground(STOP_FOREGROUND_REMOVE)
                }
                stopSelf()
            }
        }
        countDownTimer?.start()
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_ID"
        private const val NOTIFICATION_SERVICE_CHANNEL_ID = "NOTIFICATION_SERVICE_CHANNEL_ID"
        private const val NOTIFICATION_SERVICE_NAME = "NOTIFICATION_SERVICE_NAME"
        private const val NOTIFICATION_NAME = "NOTIFICATION_NAME"
        private const val NOTIFICATION_SERVICE_ID = 1
        private const val NOTIFICATION_ID = 2
        private const val TIME_DURATION = 60 * 60 * 1000L
        private const val INTERVAL = 60 * 1000L
    }
}


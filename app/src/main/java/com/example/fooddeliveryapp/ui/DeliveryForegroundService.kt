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

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "Доставка"
        private const val NOTIFICATION_ID = 1
        private const val TIME_DURATION = 50 * 60 * 1000L // 90 minutes in milliseconds
        private const val INTERVAL = 60 * 1000L // 1 minute in milliseconds
    }

    private var countDownTimer: CountDownTimer? = null

    override fun onCreate() {
        super.onCreate()
        startForeground(NOTIFICATION_ID, createNotification())
        startTimer()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(STOP_FOREGROUND_REMOVE)
        countDownTimer?.cancel()
    }

    private fun createNotification(): Notification {
        createNotificationChannel()

        val notificationText =
            "Ваш заказ будет у вас примерно через: " + getTimeDurationText(TIME_DURATION)

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(NOTIFICATION_CHANNEL_ID).setSound(null).setContentText(notificationText)
            .setSmallIcon(R.drawable.ic_app)
        return notificationBuilder.build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_NONE
            )
            channel.setSound(null,null)
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }

    private fun getTimeDurationText(timeDurationMillis: Long): String {
        val minutes = ((timeDurationMillis / (1000 * 60) + 1)).toInt()
        return "$minutes мин."
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(TIME_DURATION, INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                // Обновление текста уведомления с оставшимся временем
                val notificationText =
                    "Ваш заказ будет у вас примерно через: " + getTimeDurationText(millisUntilFinished)

                val notificationBuilder = NotificationCompat.Builder(
                    this@DeliveryForegroundService, NOTIFICATION_CHANNEL_ID
                ).setSound(null).setContentTitle(NOTIFICATION_CHANNEL_ID)
                    .setContentText(notificationText).setSmallIcon(R.drawable.ic_app)
                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
            }

            override fun onFinish() {
                // Создание уведомления "Ваш заказ доставлен" и завершение сервиса
                val notificationText = "Ваш заказ доставлен"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        "my_channel_id", "My Channel", NotificationManager.IMPORTANCE_DEFAULT
                    )
                    channel.setSound(null,null)
                    val notificationManager =
                        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.createNotificationChannel(channel)
                }

                // Создание уведомления
                val notificationBuilder =
                    NotificationCompat.Builder(applicationContext, "my_channel_id")
                        .setSmallIcon(R.drawable.ic_app)
                        .setContentTitle("Доставка")
                        .setContentText(notificationText)
                val notificationManager =
                    getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(2, notificationBuilder.build())

                // Завершение сервиса
                stopForeground(true)
                stopSelf()
            }
        }
        countDownTimer?.start()
    }
}

/*
For refactoring

Для обновления текста в foreground service без пересоздания уведомления, можно использовать следующий подход:

Создайте уведомление с использованием NotificationCompat.Builder или другого подходящего класса для построения уведомлений в Android.

Добавьте в уведомление текстовое поле (например, setContentText()) или другой элемент, в котором вы хотите обновить текст.

Сохраните ссылку на созданное уведомление или его билдер в вашем foreground service, чтобы иметь к нему доступ.

Когда необходимо обновить текст, вызовите соответствующий метод (например, setContentText()) на сохраненной ссылке уведомления или его билдера с новым текстом.

Вызовите метод NotificationManager.notify() с тем же ID уведомления, который вы использовали при его создании, чтобы обновление текста вступило в силу.

Примерный код может выглядеть следующим образом:

// Создание уведомления
NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
.setContentTitle("Заголовок")
.setContentText("Текст")
.setSmallIcon(R.drawable.ic_notification);

// Сохранение ссылки на уведомление или его билдер
Notification notification = notificationBuilder.build();

// Обновление текста в уведомлении
notificationBuilder.setContentText("Новый текст");

// Вызов метода notify() для обновления уведомления
NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
*/



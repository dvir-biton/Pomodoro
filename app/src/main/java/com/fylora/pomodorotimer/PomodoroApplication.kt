package com.fylora.pomodorotimer

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import com.fylora.pomodorotimer.domain.notifications.NotificationService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PomodoroApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return

        val channel = NotificationChannel(
            NotificationService.NOTIFICATION_CHANNEL_ID,
            "Pomodoro",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Used to show alert for finished sessions"
            setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM), null)
        }
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
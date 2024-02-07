package com.fylora.pomodorotimer.data.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.fylora.pomodorotimer.MainActivity
import com.fylora.pomodorotimer.R
import com.fylora.pomodorotimer.domain.notifications.NotificationService

class NotificationServiceImpl(
    private val context: Context
): NotificationService {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun showNotification(title: String, message: String) {
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, NotificationService.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.icon_dark)
            .setContentText(message)
            .setContentTitle(title)
            .setContentIntent(activityPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }
}
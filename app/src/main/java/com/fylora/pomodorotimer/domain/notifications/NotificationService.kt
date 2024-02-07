package com.fylora.pomodorotimer.domain.notifications

interface NotificationService {
    fun showNotification(title: String, message: String)

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "POMODORO_CHANNEL_ID"
    }
}
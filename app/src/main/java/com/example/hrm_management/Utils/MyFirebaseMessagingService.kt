package com.example.hrm_management.Utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.hrm_management.Data.Api.Api
import com.example.hrm_management.MainActivity
import com.example.hrm_management.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import javax.inject.Inject

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)


        val name = Utils.getName();

        Log.d("EMRII", name)
        // Extract notification data
        val notificationTitle = remoteMessage.notification?.title ?: "Default Title"
        val notificationText = remoteMessage.notification?.body ?: "Default Text"

        // Create a notification channel (for Android 8.0 and above)
        val channelId = "channel_id"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel Name",
                NotificationManager.IMPORTANCE_HIGH // Set importance to high for heads-up notification
            )
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Create RemoteViews for the custom layout
        val remoteViews = RemoteViews(packageName, R.layout.custom_notification)
        remoteViews.setTextViewText(R.id.notification_title, notificationTitle)
        remoteViews.setTextViewText(R.id.notification_text, notificationText)

        val notificationIntent = Intent(this, MainActivity::class.java) // Replace with the activity you want to open on notification click
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(com.google.android.material.R.drawable.ic_keyboard_black_24dp)
            .setContentIntent(pendingIntent)
            .setCustomContentView(remoteViews)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle()) // Enable custom content style
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
        // Show the notification
        val notificationId = 1234 // Change this to a unique ID for each notification
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, builder.build())
    }
}


package com.example.hrm_management.BackgroundSync

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

class SyncReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Check if the intent received is the one you want (e.g., an alarm)
        if (intent?.action == "com.example.hrm_management.SYNC_ACTION") {
            // Define constraints for the worker (e.g., require network connectivity)
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            // Create a one-time work request to run SyncWorker
            val syncWorkRequest = OneTimeWorkRequest.Builder(SyncWorker::class.java)
                .setConstraints(constraints)
                .build()

            // Enqueue the work request
            WorkManager.getInstance(context!!).enqueue(syncWorkRequest)
        }
    }
}
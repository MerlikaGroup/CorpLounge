package com.example.hrm_management

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.StatsLog.logEvent
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.WorkManager
import com.example.hrm_management.AppModule.AppComponent
import com.example.hrm_management.AppModule.DaggerAppComponent
import com.example.hrm_management.AppModule.SharedPreferencesManager
import com.example.hrm_management.AppModule.SharedPreferencesModule
import com.example.hrm_management.BackgroundSync.SyncWorker
import com.example.hrm_management.Data.Api.Api
import com.example.hrm_management.Data.Local.AppDatabase
import com.example.hrm_management.Utils.Utils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal
import java.util.concurrent.TimeUnit
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkRequest
import com.example.hrm_management.BackgroundSync.SyncReceiver

@HiltAndroidApp
class MyApplication: Application() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences


    @Inject
    lateinit var manager: SharedPreferencesManager


    @Inject
    lateinit var appDatabase: AppDatabase

    @Inject
    lateinit var Api: Api;

    override fun onCreate() {
        super.onCreate()

        Utils.initialize(this)
        FirebaseApp.initializeApp(this)
        retrieveFCMToken();

        // Set up an alarm to trigger the SyncReceiver every 15 minutes
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, SyncReceiver::class.java)
        intent.action = "com.example.hrm_management.SYNC_ACTION" // Custom action

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Schedule the alarm to repeat every 15 minutes
        val interval = 15 * 60 * 1000 // 15 minutes in milliseconds
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            interval.toLong(),
            pendingIntent
        )

        Log.d("ApplicationManager", manager.isLoggedIn().toString())
        Log.d("ApplicationManager", manager.getSession().toString())
//        if(manager.isLoggedIn()) {
//            schedulePeriodicSync();
//        }

        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        val bundle = Bundle();
        bundle.putString("Application", Build.MODEL);
        FirebaseAnalytics.getInstance(applicationContext).logEvent("Application",bundle)





    }

    @SuppressLint("StringFormatInvalid")
    private fun retrieveFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Log.d("TOKENNN", "retrieveFCMToken: ${token}")
            val msg = getString(R.string.token, token)
            Log.d("TAG", msg)
            manager.setToken(token)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })


    }

    private fun schedulePeriodicSync() {
        // Initialize WorkManager
        val workManager = WorkManager.getInstance(this)

        // Define the constraints for running the task (if any)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // Example: Require network connectivity
            .build()

        val syncWorkRequest = PeriodicWorkRequest.Builder(
            SyncWorker::class.java,
            15, // Repeat interval
            TimeUnit.MINUTES // Time unit
        )
            .setConstraints(constraints)
            .build()

        // Enqueue the work request
        workManager.enqueue(syncWorkRequest)
    }
}

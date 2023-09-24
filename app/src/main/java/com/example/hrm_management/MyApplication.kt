package com.example.hrm_management

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.StatsLog.logEvent
import android.widget.Toast
import com.example.hrm_management.AppModule.AppComponent
import com.example.hrm_management.AppModule.DaggerAppComponent
import com.example.hrm_management.AppModule.SharedPreferencesModule
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

@HiltAndroidApp
class MyApplication: Application() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences



    @SuppressLint("StringFormatInvalid")
    override fun onCreate() {
        super.onCreate()

        Utils.initialize(this)
        FirebaseApp.initializeApp(this)
        retrieveFCMToken();


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
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })


    }
}

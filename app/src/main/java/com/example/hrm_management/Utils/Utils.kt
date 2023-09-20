package com.example.hrm_management.Utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.LocaleList
import android.provider.Settings
import androidx.annotation.RequiresApi

class Utils {

    // Function to get the Android ID
    @SuppressLint("HardwareIds")
    fun getAndroidID(context: Context): String {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        ) ?: ""
    }

    // Function to get the current locale
    @RequiresApi(Build.VERSION_CODES.N)
    fun getCurrentLocale(context: Context): LocaleList {
        return context.resources.configuration.locales;
    }
}
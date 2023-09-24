package com.example.hrm_management.AppModule

import android.content.Context

object SharedPreferencesManagerSingleton {
    private var instance: SharedPreferencesManager? = null

    fun getInstance(context: Context): SharedPreferencesManager {
        if (instance == null) {
            // Create the instance if it doesn't exist
            val sharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
            instance = SharedPreferencesManagerImpl(sharedPreferences)
        }
        return instance!!
    }
}
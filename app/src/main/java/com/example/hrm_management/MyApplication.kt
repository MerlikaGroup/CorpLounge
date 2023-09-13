package com.example.hrm_management

import android.app.Application
import android.content.SharedPreferences
import com.example.hrm_management.AppModule.AppComponent
import com.example.hrm_management.AppModule.DaggerAppComponent
import com.example.hrm_management.AppModule.SharedPreferencesModule
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication: Application() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()
    }
}

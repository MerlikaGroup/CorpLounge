package com.example.hrm_management.AppModule

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.hrm_management.MyApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class) // or ApplicationComponent if needed
@Module
class SharedPreferencesModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
    }

    @Provides
    fun provideSharedPreferencesManager(sharedPreferences: SharedPreferences): SharedPreferencesManager {
        return SharedPreferencesManagerImpl(sharedPreferences)
    }
}


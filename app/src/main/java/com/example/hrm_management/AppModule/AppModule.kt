package com.example.hrm_management.AppModule

import android.app.Application
import com.example.hrm_management.BackgroundSync.SyncWorker
import com.example.hrm_management.Data.Api.Api
import com.example.hrm_management.Data.Api.Interceptor
import com.example.hrm_management.Data.Local.AppDatabase
import com.example.hrm_management.Data.Local.ConfigurationListDao
import com.example.hrm_management.Data.Local.UserDao
import com.example.hrm_management.Utils.SyncManager
import com.example.hrm_management.Utils.Utils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // or ApplicationComponent if needed
class AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return AppDatabase.getInstance(application)
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideConfigurationListDao(database: AppDatabase): ConfigurationListDao {
        return database.configurationListDao()
    }

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor {
        return Interceptor() // Create an instance of your custom interceptor
    }

    @Provides
    @Singleton
    fun provideRetrofit(interceptor: Interceptor): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        // Define your base URL here
        val BASE_URL = Utils.getBaseURL();

        return Retrofit.Builder()
            .baseUrl(BASE_URL) // You can still set the base URL here
            .client(okHttpClient) // Use the custom OkHttpClient with the interceptor
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideSyncManager(
        appDatabase: AppDatabase,
        manager: SharedPreferencesManager,
        api: Api
    ): SyncManager {
        return SyncManager(appDatabase, manager, api)
    }



}

package com.example.hrm_management.AppModule

import android.app.Application
import com.example.hrm_management.Data.Api.Api
import com.example.hrm_management.Data.Api.Interceptor
import com.example.hrm_management.Data.Local.AppDatabase
import com.example.hrm_management.Data.Local.ConfigurationListDao
import com.example.hrm_management.Data.Local.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class) // or ApplicationComponent if needed
@Module
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
        val BASE_URL = "http://192.168.1.66:3001/"

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
}

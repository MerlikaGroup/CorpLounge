package com.example.hrm_management.BackgroundSync

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.hrm_management.AppModule.SharedPreferencesManager
import com.example.hrm_management.Data.Api.Api
import com.example.hrm_management.Data.Local.AppDatabase
import com.example.hrm_management.MyApplication
import com.example.hrm_management.Utils.SyncManager
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject


class SyncWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {
    override fun doWork(): Result {
        // Retrieve appdatabase, manager, and api using the @Inject annotation
        val appDatabase: AppDatabase = (applicationContext as MyApplication).appDatabase
        val manager: SharedPreferencesManager = (applicationContext as MyApplication).manager
        val api: Api = (applicationContext as MyApplication).Api

        val syncManager = SyncManager(appDatabase, manager, api)
        syncManager.sync() // Call the function you want to run in the background
        return Result.success() // Return success or failure based on the result of your operation
    }
}


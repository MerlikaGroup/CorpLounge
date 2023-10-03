package com.example.hrm_management.Views.Menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hrm_management.AppModule.SharedPreferencesManager
import com.example.hrm_management.Data.Api.Api
import com.example.hrm_management.Data.Local.AppDatabase
import com.example.hrm_management.Utils.SyncManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    val database: AppDatabase,
    val manager: SharedPreferencesManager,
    val api: Api,
    val syncManager: SyncManager

) : ViewModel() {

    private val _loadingState = MutableLiveData<TaskPhase>()
    val loadingState: LiveData<TaskPhase> get() = _loadingState

    suspend fun sync() {
        // Perform synchronous operations
        val result = withContext(Dispatchers.IO) {
            // Perform asynchronous I/O operations here
            syncManager.sync()
        }
        // Handle the result if needed
    }

    fun setLoadingState(phase: TaskPhase) {
        _loadingState.value = phase
    }

}
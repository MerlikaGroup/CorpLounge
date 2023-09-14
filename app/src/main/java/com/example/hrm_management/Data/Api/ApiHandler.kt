package com.example.hrm_management.Data.Api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import retrofit2.await


class ApiHandler(private val api: Api) {
    suspend fun <T : Any> Call<T>.await(): Result<T> {
        return try {
            val response: Response<T> = withContext(Dispatchers.IO) {
                execute()
            }
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("API request failed with code ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error("API request failed with error: ${e.message}")
        }
    }
}
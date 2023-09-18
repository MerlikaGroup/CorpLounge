package com.example.hrm_management.Data.Api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

object ApiUtils {
    fun <T> performApiCall(apiCall: Call<T>): Result<Any> {
        return try {
            val response: Response<T> = apiCall.execute()

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
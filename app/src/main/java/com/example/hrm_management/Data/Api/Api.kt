package com.example.hrm_management.Data.Api

import com.example.hrm_management.Data.Api.Model.*
import com.example.hrm_management.Data.Local.ConfigurationList
import com.example.hrm_management.Data.Local.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @Headers("Content-Type: application/json")
    @POST("/getConfig") // Replace with your actual POST endpoint
    fun getConfigurations(@Body request: ConfigurationRequest): Call<ConfigurationsResponse>;


    @Headers("Content-Type: application/json")
    @POST("/login") // Replace with your actual POST endpoint
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}
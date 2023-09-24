package com.example.hrm_management.Data.Api

import com.example.hrm_management.Data.Api.Model.LoginRequest
import com.example.hrm_management.Data.Api.Model.LoginResponse
import com.example.hrm_management.Data.Api.Model.UserResponse
import com.example.hrm_management.Data.Local.ConfigurationList
import com.example.hrm_management.Data.Local.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @GET("/user/1")
    fun getConfigs(): Call<UserResponse>;


    @Headers("Content-Type: application/json")
    @POST("/login") // Replace with your actual POST endpoint
    fun login(@Body request: LoginRequest): Call<LoginResponse>;
}
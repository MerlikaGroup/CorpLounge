package com.example.hrm_management.Data.Api

import com.example.hrm_management.Data.Api.Model.UserResponse
import com.example.hrm_management.Data.Local.ConfigurationList
import retrofit2.Call
import retrofit2.http.GET

interface Api {

    @GET("/user/1")
    fun getConfigs(): Call<UserResponse>;
}
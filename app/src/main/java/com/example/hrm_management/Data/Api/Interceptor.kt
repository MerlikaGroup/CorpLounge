package com.example.hrm_management.Data.Api

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class Interceptor() : Interceptor {

    // Define your base URL here

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .url(originalRequest.url) // Use the base URL defined in interceptor
            .build()
        return chain.proceed(newRequest)
    }
}
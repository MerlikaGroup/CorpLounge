package com.example.hrm_management.Data.Api.Model

// Response model for User entity
data class UserResponse(
    val userID: Int,
    val username: String,
    val password: String,
    val photo: String?, // This can be a nullable string if "photo" can be null
    val registrationDate: String,
    val role: Int,
    val configurations: List<ConfigurationResponse>
)

data class ConfigurationResponse(
    val configurationName: String,
    val value: String
)
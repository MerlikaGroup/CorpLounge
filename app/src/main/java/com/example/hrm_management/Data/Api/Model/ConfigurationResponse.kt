package com.example.hrm_management.Data.Api.Model

data class ConfigurationsResponse(
    val userID: Int,
    val username: String,
    val password: String,
    val photo: String, // This can be nullable if the "Photo" field is optional
    val registrationDate: String,
    val role: Int,
    val token: String,
    val fcm_token: String,
    val configurations: List<ConfigurationLists>
)

data class ConfigurationLists(
    val configurationName: String,
    val value: String
)
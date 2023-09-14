package com.example.hrm_management.Data.Api.Model

// Response model for ConfigurationList entity
data class ConfigurationList(
    val configurationId: Int,
    val configurationName: String?,
    val value: String?,
    val userId: Int
)



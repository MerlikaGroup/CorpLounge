package com.example.hrm_management.Data.Local

import androidx.room.ColumnInfo
import androidx.room.Entity

import androidx.room.PrimaryKey


@Entity(tableName = "ConfigurationList")
data class ConfigurationList (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ConfigurationID")
    val configurationId: Int = 1,

    @ColumnInfo(name = "ConfigurationName")
    val configurationName: String? = null,

    @ColumnInfo(name = "Value")
    val value: String? = null,

    @ColumnInfo(name = "UserID")
    val userId: Int = 0 // Constructors, getters, and setters
)

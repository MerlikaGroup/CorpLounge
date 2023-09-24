package com.example.hrm_management.Data.Local

import androidx.room.ColumnInfo
import androidx.room.Entity

import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "User")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "UserID")
    val userId: Int,

    @ColumnInfo(name = "Username")
    val username: String?,

    @ColumnInfo(name = "Password")
    val password: String?,

    @ColumnInfo(name = "Photo")
    val photo: String?, // Assuming your BLOB data is stored as a byte array

    @ColumnInfo(name = "RegistrationDate")
    val registrationDate: Long?, // Assuming DATETIME is stored as Long

    @ColumnInfo(name = "Role")
    val role: Int,

    @ColumnInfo(name = "Token")
    val token: String
)





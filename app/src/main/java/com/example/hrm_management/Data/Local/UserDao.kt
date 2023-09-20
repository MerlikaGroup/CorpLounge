package com.example.hrm_management.Data.Local

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User?)

    @Update
    fun update(user: User?)

    @Delete
    fun delete(user: User?)

    @Query("SELECT * FROM User WHERE UserID = :userId")
    fun getUserById(userId: Int): User// Define other queries as needed
}

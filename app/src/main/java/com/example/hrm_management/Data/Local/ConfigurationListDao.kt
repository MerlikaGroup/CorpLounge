package com.example.hrm_management.Data.Local

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ConfigurationListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(configurationList: ConfigurationList?)

    @Update
    fun update(configurationList: ConfigurationList?)

    @Delete
    fun delete(configurationList: ConfigurationList?)

    @Query("SELECT * FROM ConfigurationList")
    fun getAllConfigurations(): List<ConfigurationList>

    @Query("SELECT * FROM ConfigurationList WHERE UserID = :userId")
    fun getConfigurationsByUserId(userId: Int): LiveData<List<ConfigurationList?>?>? // Define other queries as needed

    @Query("SELECT * FROM configurationlist WHERE ConfigurationName = :name and ConfigurationID = 1")
    suspend fun getConfigurationItemByName(name: String): ConfigurationList?
}

package com.example.hrm_management.Data.Local

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.hrm_management.AppModule.SharedPreferencesManager
import javax.inject.Inject

@Database(entities = [User::class, ConfigurationList::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun configurationListDao(): ConfigurationListDao

    @Inject
    lateinit var manager: SharedPreferencesManager;


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addMigrations(Migration1to2) // Add your migration from version 1 to version 2
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

val Migration1to2: Migration = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Update the migration logic to add the new column
        database.execSQL("ALTER TABLE User ADD COLUMN new_column_name TEXT")
    }
}

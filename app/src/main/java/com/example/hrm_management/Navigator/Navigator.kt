package com.example.hrm_management.Navigator

import android.content.Context
import android.content.Intent
import com.example.hrm_management.MainActivity
import com.example.hrm_management.Views.Menu.MenuActivity

class Navigator(private val context: Context) {

    fun navigateTo(destination: Destination) {
        val intent = when (destination) {
            is Destination.Menu -> Intent(context, MenuActivity::class.java)
            is Destination.Login -> Intent(context, MainActivity::class.java)
            // Add more cases for other destinations
        }
        context.startActivity(intent)
    }
}

sealed class Destination {
    object Menu : Destination()
    object Login : Destination()
    // Define other destinations as needed
}
package com.example.hrm_management

import Utils
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.hrm_management.AppModule.SharedPreferencesManager
import com.example.hrm_management.Utils.LocaleHelper
import com.example.hrm_management.Views.Login.LoginFragment
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var manager: SharedPreferencesManager;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val locales = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
           Utils().getCurrentLocale(this)
       } else {
           TODO("VERSION.SDK_INT < N")
       }

        Log.d("manager", manager.getMenuList().toString())


        // Replace the initial fragment with the LoginFragment
        replaceFragment(LoginFragment())
        // You can use the injected SharedPreferences (manager) here

    }

     fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment) // R.id.fragmentContainer is the ID of the layout container where you want to place the fragment
            .addToBackStack(null) // Add the transaction to the back stack
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        Log.d("Manager", "backkkkkkkk")

    }

    fun onLanguageButtonClick(view: View) {
        // Change the locale to the desired language code
        LocaleHelper.setLocale(this, manager.getLanguage()) // Replace "fr" with your desired language code

        // Restart the activity to apply the locale change
        recreate()
    }

}
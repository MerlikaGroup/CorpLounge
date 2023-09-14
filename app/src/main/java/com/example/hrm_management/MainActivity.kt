package com.example.hrm_management

import SyncManager
import Utils
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.hrm_management.AppModule.SharedPreferencesManager
import com.example.hrm_management.Data.Api.Api
import com.example.hrm_management.Data.Api.ApiUtils
import com.example.hrm_management.Data.Api.Result
import com.example.hrm_management.Data.Local.AppDatabase
import com.example.hrm_management.Utils.LocaleHelper
import com.example.hrm_management.Views.Login.Intro
import com.example.hrm_management.Views.Login.LoginFragment
import com.example.hrm_management.Views.OnBoarding.OnboardingPagerAdapter
import com.example.hrm_management.Views.Register.RegisterFragment
import com.example.hrm_management.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var manager: SharedPreferencesManager;
    @Inject
    lateinit var database: AppDatabase;

    @Inject
    lateinit var api: Api;

    lateinit var mContext: Context;


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val viewpager = binding.viewPager;
        val tabLayout = binding.tabLayout;
        val adapter = OnboardingPagerAdapter(this)


        // Create an instance of SyncManager
        val syncManager = SyncManager(database, manager)

        // Call the synchronization method on a background thread
        Thread {
            syncManager.insertConfigurationValues();

            // Update UI or perform other tasks after synchronization
            runOnUiThread {
                Log.d("Menu", manager.getMenuList().toString())
            }
        }.start()

        GlobalScope.launch(Dispatchers.Main) {
            delay(2000);
            val userCall = api.getConfigs() // Replace with your actual API call function from 'Api'
            val userResult = ApiUtils.performApiCall(userCall)

            when (userResult) {
                is Result.Success -> { // Access Success directly
                    val userResponse = userResult.data
                    // Handle successful response
                    Log.d("Menu", userResponse.toString())
                }
                is Error -> { // Access Error directly
                    val errorMessage = userResult.message
                    // Handle error
                    if (errorMessage != null) {
                        Log.e("API Error", errorMessage)
                    }
                }
            }
        }




// Add your onboarding fragments to the adapter
        adapter.addFragment(Intro())
        adapter.addFragment(LoginFragment())

        viewpager.adapter = adapter;

// Link the TabLayout with the ViewPager
        TabLayoutMediator(tabLayout, viewpager) { tab, position ->
            // Set tab titles if needed
        }.attach()

       val locales = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
           Utils().getCurrentLocale(this)
       } else {
           TODO("VERSION.SDK_INT < N")
       }

        Log.d("manager", manager.getMenuList().toString())


        // Replace the initial fragment with the LoginFragment
//        replaceFragment(LoginFragment())
        // You can use the injected SharedPreferences (manager) here

    }

//     fun replaceFragment(fragment: Fragment) {
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.container, fragment) // R.id.fragmentContainer is the ID of the layout container where you want to place the fragment
//            .addToBackStack(null) // Add the transaction to the back stack
//            .commit()
//    }

    override fun onBackPressed() {
        super.onBackPressed()

        Log.d("Manager", "backkkkkkkk")

    }

    fun onLanguageButtonClick(view: View) {
        // Change the locale to the desired language code
        LocaleHelper.setLocale(this, manager.getLanguage()) // Replace "fr" with your desired language code

        // Restart the activity to apply the locale change
        recreate()
        //Start project
    }

}
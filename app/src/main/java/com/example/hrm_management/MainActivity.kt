package com.example.hrm_management

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.example.hrm_management.AppModule.SharedPreferencesManager
import com.example.hrm_management.BackgroundSync.SyncWorker
import com.example.hrm_management.Data.Api.Api
import com.example.hrm_management.Data.Local.AppDatabase
import com.example.hrm_management.Utils.LocaleHelper
import com.example.hrm_management.Utils.Utils
import com.example.hrm_management.Views.Login.Intro
import com.example.hrm_management.Views.Login.LoginFragment
import com.example.hrm_management.Views.Menu.MenuActivity
import com.example.hrm_management.Views.OnBoarding.OnboardingPagerAdapter
import com.example.hrm_management.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var manager: SharedPreferencesManager;
    @Inject
    lateinit var database: AppDatabase;

    @Inject
    lateinit var api: Api;

    lateinit var mContext: Context;

    private val viewModel: MainViewModel by viewModels()



    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val device = Build.MODEL;
        FirebaseAnalytics.getInstance(this).setUserId(device)

        val viewpager = binding.viewPager;
        val tabLayout = binding.tabLayout;
        val adapter = OnboardingPagerAdapter(this)


//       viewModel.sync();
//        viewModel.generatePDF();

        if(manager.isLoggedIn()){
// User is logged in, navigate to MenuActivity
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
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
           Utils.getCurrentLocale(this)
       } else {
           TODO("VERSION.SDK_INT < N")
       }

        Log.d("manager", manager.getMenuList().toString())


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
        //Start project
    }

}
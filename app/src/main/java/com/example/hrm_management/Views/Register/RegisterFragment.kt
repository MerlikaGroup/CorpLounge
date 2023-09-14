package com.example.hrm_management.Views.Register

import Utils
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.example.hrm_management.AppModule.SharedPreferencesManager
import com.example.hrm_management.Data.Local.*
import com.example.hrm_management.MainActivity
import com.example.hrm_management.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.log

import com.example.hrm_management.databinding.FragmentRegisterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    @Inject
    lateinit var manager: SharedPreferencesManager;

    @Inject
    lateinit var Database: AppDatabase;

    @Inject
    lateinit var userdao: UserDao;

    @Inject
    lateinit var configurationDao: ConfigurationListDao;

    lateinit var user: User;

    private lateinit var binding: FragmentRegisterBinding // ViewBinding instance

    // Inside your Activity or Fragment
    private val mainScope = MainScope()


    var counter = 0;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root

        // Set the background color based on manager.getColor()
        val color = manager.getThemeColor()
        binding.myButton.setBackgroundColor(color)

        // Get Android ID using Utils
        val imei = Utils().getAndroidID(requireContext())
        Log.d("ManagerUtils", imei.toString());
        Log.d("ManagerDatabase", userdao.getUserById(0).toString())


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Now you can use binding to access your views
        binding.myButton.setOnClickListener {
            someFunction();

        }
    }



    // Method to get MainActivity reference
    fun getMainActivity(): MainActivity? {
        return activity as? MainActivity
    }

    // Example usage outside of lifecycle methods
    fun someFunction() {
        val mainActivity = getMainActivity()
        if (mainActivity != null) {

            var user = User(
                userId = 1, // Provide a unique user ID
                username = "example_username",
                password = "example_password",
                photo = "example_photo", // Replace with actual photo data
                registrationDate = System.currentTimeMillis(), // Use the current time as registration date
                role = 1 // Assign a role as needed
            )

            mainScope.launch(Dispatchers.IO) {
                // Perform database insertion on the background thread
                userdao.insert(user)
            }


            val configuration = ConfigurationList(
                configurationId = 1, // Provide a unique configuration ID
                configurationName = "example_configuration_name",
                value = "example_value",
                userId = 1 // Provide the associated user ID
            )
            mainScope.launch(Dispatchers.IO) {
                // Perform database insertion on the background thread
                configurationDao.insert(configuration)
            }
            val userLiveData: LiveData<User?>? = userdao.getUserById(1)

            if (userLiveData != null) {
                userLiveData.observe(requireActivity(), Observer { user ->
                    // Handle changes to the user data here
                    if (user != null) {
                        Log.d("ManagerDatabase", user.toString())
                    }
                })
            }


        }
    }

    fun onLanguageButtonClick(view: View) {
     val mainactivity = getMainActivity();
        mainactivity?.onLanguageButtonClick(view)
    }

}

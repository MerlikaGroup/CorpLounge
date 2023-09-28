package com.example.hrm_management.Views.Register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hrm_management.AppModule.SharedPreferencesManager
import com.example.hrm_management.Data.Local.*
import com.example.hrm_management.MainActivity
import com.example.hrm_management.Utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

import com.example.hrm_management.databinding.FragmentRegisterBinding
import kotlinx.coroutines.MainScope

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
        val imei = Utils.getAndroidID(requireContext())
        Log.d("ManagerUtils", imei.toString());
        Log.d("ManagerDatabase", userdao.getUserById(0).toString())


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Now you can use binding to access your views
        binding.myButton.setOnClickListener {


        }
    }



    // Method to get MainActivity reference
//    fun getMainActivity(): MainActivity? {
//        return activity as? MainActivity
//    }



//    fun onLanguageButtonClick(view: View) {
//     val mainactivity = getMainActivity();
//        mainactivity?.onLanguageButtonClick(view)
//    }

}

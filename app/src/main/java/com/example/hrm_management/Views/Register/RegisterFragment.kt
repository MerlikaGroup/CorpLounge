package com.example.hrm_management.Views.Register

import Utils
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.hrm_management.AppModule.SharedPreferencesManager
import com.example.hrm_management.MainActivity
import com.example.hrm_management.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.log

import com.example.hrm_management.databinding.FragmentRegisterBinding

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    @Inject
    lateinit var manager: SharedPreferencesManager

    private lateinit var binding: FragmentRegisterBinding // ViewBinding instance


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
        Log.d("ManagerUtils", imei.toString())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Now you can use binding to access your views
        binding.myButton.setOnClickListener {
            onLanguageButtonClick(view)

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
            // Now you can access methods or properties of MainActivity
            counter += 1;

            if(counter % 2 == 0){
                mainActivity.setTitle("Cift")
            }
            else{
                mainActivity.setTitle("Tek")
            }


        }
    }

    fun onLanguageButtonClick(view: View) {
     val mainactivity = getMainActivity();
        mainactivity?.onLanguageButtonClick(view)
    }

}

package com.example.hrm_management.Views.Login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hrm_management.AppModule.SharedPreferencesManager
import com.example.hrm_management.MainActivity
import com.example.hrm_management.R
import com.example.hrm_management.Views.Register.RegisterFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment() {

    @Inject
    lateinit var manager: SharedPreferencesManager;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("ManagerFragment", manager.getMenuList())

        val mainActivity = requireActivity() as MainActivity


        manager.setMenuList("CHANGEDDDDDDDDD")

        // Replace the current fragment with another fragment
//        val fragmentToReplace = RegisterFragment() // Replace with the desired fragment
//        mainActivity.replaceFragment(fragmentToReplace)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

}
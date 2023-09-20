package com.example.hrm_management.Views.Login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hrm_management.AppModule.SharedPreferencesManager
import com.example.hrm_management.databinding.FragmentIntroBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Intro : Fragment() {

    @Inject
    lateinit var manager: SharedPreferencesManager;

    lateinit var binding: FragmentIntroBinding;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIntroBinding.inflate(inflater, container, false)
        val rootView = binding.root




        return rootView;
    }
}

package com.example.hrm_management.Views.Login

import Utils
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGParseException
import com.example.hrm_management.AppModule.SharedPreferencesManager
import com.example.hrm_management.Data.Local.AppDatabase
import com.example.hrm_management.Data.Local.ConfigurationList
import com.example.hrm_management.Data.Local.User
import com.example.hrm_management.Data.Local.UserDao
import com.example.hrm_management.R
import com.example.hrm_management.databinding.FragmentIntroBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

        // Load and set the SVG image
        val svgDrawable: Drawable? = VectorDrawableCompat.create(resources, R.drawable.intro, null)
        binding.svgImageView.setImageDrawable(svgDrawable)



        return rootView;
    }
}

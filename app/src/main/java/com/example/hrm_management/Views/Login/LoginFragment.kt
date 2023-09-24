package com.example.hrm_management.Views.Login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import com.example.hrm_management.AppModule.SharedPreferencesManager
import com.example.hrm_management.MainActivity
import com.example.hrm_management.R
import com.example.hrm_management.Utils.SyncManager
import com.example.hrm_management.Views.Register.RegisterFragment
import com.example.hrm_management.databinding.ActivityMainBinding
import com.example.hrm_management.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.concurrent.thread


@AndroidEntryPoint
class LoginFragment : Fragment() {

    @Inject
    lateinit var manager: SharedPreferencesManager;

    @Inject
    lateinit var syncmanager: SyncManager;

    private lateinit var binding: FragmentLoginBinding;

    private lateinit var bindingActivity: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("ManagerFragment", manager.getMenuList())

        val mainActivity = requireActivity() as MainActivity


        // Replace the current fragment with another fragment
//        val fragmentToReplace = RegisterFragment() // Replace with the desired fragment
//        mainActivity.replaceFragment(fragmentToReplace)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater,container,false)

        val pulseAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.pulse_animation)
        val cardView = binding.cardview3 // Replace 'yourCardView' with your CardView's ID
        cardView.startAnimation(pulseAnimation)

        val shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake_animation)
        val button = binding.loginButton;

        val mainActivityBinding = (requireActivity() as MainActivity).binding



        val progressBar: ProgressBar = mainActivityBinding.progressBar;


// Call this function from your button click listener
        button.setOnClickListener {
            button.startAnimation(shakeAnimation)

            // Show the ProgressBar
            progressBar.visibility = View.VISIBLE

            // Launch a coroutine to perform the syncUser operation
            CoroutineScope(Dispatchers.Main).launch {
                val result = syncUser(
                    binding.usernameEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                )

                // Hide the ProgressBar
                progressBar.visibility = View.GONE

                // Handle the result here
                if (result) {
                    // Sync was successful
                } else {
                    // Handle the case where sync failed
                }
            }
        }



        return binding.root;
    }


    // Modify the syncUser function to return a suspend function
    suspend fun syncUser(username: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                // Replace this with the actual logic to perform user synchronization
                val result = syncmanager.syncUser(username, password)

                // Return true if the synchronization was successful
                true
            } catch (e: Exception) {
                // Handle any exceptions and return false if there was a failure
                false
            }
        }
    }


}
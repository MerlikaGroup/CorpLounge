package com.example.hrm_management.Views.Login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.hrm_management.AppModule.SharedPreferencesManager
import com.example.hrm_management.MainActivity
import com.example.hrm_management.MainViewModel
import com.example.hrm_management.R
import com.example.hrm_management.Utils.SyncManager
import com.example.hrm_management.Views.Menu.MenuActivity
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

    private val sharedViewModel: MainViewModel by viewModels({ requireActivity() }) // Access the ViewModel shared with the activity


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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        // Bind the ViewModel to the layout using the specific binding class
        binding.viewModel = sharedViewModel;
        binding.lifecycleOwner = this

        val pulseAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.pulse_animation)
        val cardView = binding.cardview3 // Replace 'yourCardView' with your CardView's ID
        cardView.startAnimation(pulseAnimation)




        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        // Set the EditorActionListener on the username EditText
        binding.usernameEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                // Move focus to the password EditText when "Next" is pressed
                binding.passwordEditText.requestFocus()
                true
            } else {
                false
            }
        }


        val shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake_animation)

        sharedViewModel.clicked.observe(viewLifecycleOwner){
            val button = binding.loginButton;
            button.startAnimation(shakeAnimation)
        }

        sharedViewModel.loginResult.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                // Navigate to the next activity
                val intent = Intent(requireContext(), MenuActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            } else {
                // Handle login failure
            }
        }


    }




}
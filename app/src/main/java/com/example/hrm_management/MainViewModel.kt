package com.example.hrm_management

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.LocaleList
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.annotation.Keep
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrm_management.AppModule.SharedPreferencesManager
import com.example.hrm_management.Data.Api.Api
import com.example.hrm_management.Data.Api.Model.LoginResponse
import com.example.hrm_management.Data.Local.AppDatabase
import com.example.hrm_management.Data.Local.ConfigurationList
import com.example.hrm_management.Navigator.Destination
import com.example.hrm_management.Navigator.Navigator
import com.example.hrm_management.Utils.LocaleHelper
import com.example.hrm_management.Utils.SyncManager
import com.example.hrm_management.Utils.Utils
import com.example.hrm_management.Views.Menu.MenuDataClass.MenuItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Locale
import javax.inject.Inject

@Keep
@HiltViewModel
class MainViewModel @Inject constructor(
     val database: AppDatabase,
     val manager: SharedPreferencesManager,
     val api: Api,
     val syncManager: SyncManager

) : ViewModel() {


    var username: String = ""
    var password: String = ""

    private val _clicked = MutableLiveData<Boolean>()
    val clicked: LiveData<Boolean> = _clicked

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    private val _isNetworkRequestInProgress = MutableLiveData<Boolean>()
    val isNetworkRequestInProgress: LiveData<Boolean> = _isNetworkRequestInProgress


    fun isLoggedIn(): Boolean {
        return manager.isLoggedIn()
    }




    fun onLoginButtonClick(username: String, password: String) {

        _clicked.postValue(true)
        // Show the ProgressBar immediately
        _isNetworkRequestInProgress.postValue(true)

        // Perform the network request asynchronously
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val startTime = System.currentTimeMillis()

                val loginResponse = syncManager.syncUser(username, password)

                // Update LiveData on the main thread
                withContext(Dispatchers.Main) {
                    // Hide the ProgressBar when the request is complete
                    delay(1000)
                    _isNetworkRequestInProgress.postValue(false)

                    if (loginResponse != null) {
                        // Login was successful, set the loginResult LiveData to true
                        _loginResult.postValue(true)
                    } else {
                        // Handle login failure, set the loginResult LiveData to false
                        _loginResult.postValue(false)
                    }
                }
            } catch (e: Exception) {
                // Handle exceptions here, e.g., show an error message
                e.printStackTrace()
            }
        }
    }





    fun getMenuList(): String {
        // Replace YourMenuListType with the actual type of your menu list
        return manager.getMenuList()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getCurrentLocale(context: Context): LocaleList {
        return Utils.getCurrentLocale(context) // You may need to pass a context here if required
    }

    fun setLocale(context: Context, languageCode: String) {
        LocaleHelper.setLocale(context, languageCode) // You may need to pass a context here if required
    }


//    fun sync() {
//
//        syncManager.sync()
//        viewModelScope.launch(Dispatchers.IO) {
//            // Fetch the User object from the database
//             val fetchedUser = database.configurationListDao().getConfigurationItemByName("MenuList")
//
//            // Check if the fetchedUser is not null
//            if (fetchedUser != null) {
//                // Assign the fetched user to the username property
//                username = fetchedUser
//
//                // Access and log the username property
//                Log.d("usernameee", username?.value.toString());
//
//                // Update your UI or ViewModel properties here if needed
//            } else {
//                // Handle the case where fetchedUser is null
//                Log.e("usernameee", "Fetched user is null")
//            }
//        }
//    }

//    fun generatePDF(){
//        val image = loadSignatureImage();
//        generateEmployeePDF(mContext, mDatabase.configurationListDao().getAllConfigurations(),image)
//    }


    fun loadSignatureImage(): Bitmap? {
        try {
            val emulatorDownloadsPath =
                "/sdcard/Download" // Path within the emulator's internal storage for Downloads
            val signatureFileName = "Download.bmp" // Replace with the actual file name

            val signatureFile = File(emulatorDownloadsPath, signatureFileName)

            if (signatureFile.exists()) {
                // Load the signature image from the file
                val signatureBitmap = BitmapFactory.decodeFile(signatureFile.absolutePath)
                if (signatureBitmap != null) {
                    // Successfully loaded the image
                    return signatureBitmap
                } else {
                    Log.e("SignatureImage", "BitmapFactory.decodeFile returned null")
                }
            } else {
                // Log a message if the file does not exist
                Log.e("SignatureImage", "Signature image file does not exist.")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Log any exceptions that occur during image loading
            Log.e("SignatureImage", "Error loading signature image: ${e.message}")
        }

        return null
    }

}





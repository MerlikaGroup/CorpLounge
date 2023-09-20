package com.example.hrm_management

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrm_management.AppModule.SharedPreferencesManager
import com.example.hrm_management.Data.Api.Api
import com.example.hrm_management.Data.Local.AppDatabase
import com.example.hrm_management.Data.Local.ConfigurationList
import com.example.hrm_management.Utils.SyncManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
     val database: AppDatabase,
     val manager: SharedPreferencesManager,
     val api: Api,
     val syncManager: SyncManager

) : ViewModel() {


    private var username: ConfigurationList? = null
        get() = field                    // getter
        set(value) { field = value }



    fun sync() {

        syncManager.sync()
        viewModelScope.launch(Dispatchers.IO) {
            // Fetch the User object from the database
            val fetchedUser = database.configurationListDao().getConfigurationItemByName("MenuList")

            // Check if the fetchedUser is not null
            if (fetchedUser != null) {
                // Assign the fetched user to the username property
                username = fetchedUser

                // Access and log the username property
                Log.d("usernameee", username?.value.toString());

                // Update your UI or ViewModel properties here if needed
            } else {
                // Handle the case where fetchedUser is null
                Log.e("usernameee", "Fetched user is null")
            }
        }
    }

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





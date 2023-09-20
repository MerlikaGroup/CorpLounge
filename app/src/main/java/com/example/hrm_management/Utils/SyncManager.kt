package com.example.hrm_management.Utils

import android.util.Log
import com.example.hrm_management.AppModule.SharedPreferencesManager
import com.example.hrm_management.Data.Api.Api
import com.example.hrm_management.Data.Local.AppDatabase
import com.example.hrm_management.Data.Api.Model.ConfigurationResponse
import com.example.hrm_management.Data.Api.Model.UserResponse
import com.example.hrm_management.Data.Local.ConfigurationList
import com.example.hrm_management.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class SyncManager @Inject constructor(
    private val appdatabase: AppDatabase,
    private val manager: SharedPreferencesManager,
    private val api: Api
) {

    fun sync() {
        try {

            syncConfigurations();

//--------------------------------------------------------------------------------------
//             when (userResult) {
//                 is Result.Success -> { // Access Success directly
//                     val userResponse = userResult.data
//                     // Handle successful response
//                     Log.d("MenufromAPI", userResponse.toString())
//
//                 }
//                 is Error -> { // Access Error directly
//                     val errorMessage = userResult.message
//                     // Handle error
//                     if (errorMessage != null) {
//                         Log.e("API Error", errorMessage)
//                     }
//                 }
//                 else -> {}
//             }
            //------------------------------------------------------------
        } catch (e: Exception) {
            // Handle errors, e.g., database or SharedPreferences errors
            e.printStackTrace()
        }

        Log.d("From API", manager.getMenuList().toString())
    }

    fun insertConfigurationValues() {
        // Insert configuration values from AppConfigurations into the table
        appdatabase.configurationListDao()
            .insert(ConfigurationList(1, "MenuList", manager.getMenuList(), 1));
        appdatabase.configurationListDao()
            .insert(ConfigurationList(2, "Currency", manager.getCurrency(), 1));

        appdatabase.configurationListDao()
            .insert(ConfigurationList(3, "Language", manager.getLanguage(), 1));

        appdatabase.configurationListDao()
            .insert(ConfigurationList(4, "BankTransfer", manager.getBankTransfer().toString(), 1));

        appdatabase.configurationListDao()
            .insert(ConfigurationList(5, "Cash", manager.getCash().toString(), 1));

        appdatabase.configurationListDao()
            .insert(ConfigurationList(6, "CreatePDF", manager.getCreatePDF().toString(), 1));


    }

    // Function to get color resource ID by color name
    private fun getThemeColorByName(colorName: String): Int {
        return when (colorName.toLowerCase()) {
            "blue" -> R.color.blue // Replace with your actual color resource names
            "red" -> R.color.red
            // Add more cases for other color names
            else -> 0 // Default color if no match is found
        }
    }

    private fun syncConfigurations() {
        try {
            val userrespone: Call<UserResponse> = api.getConfigs()

            userrespone.enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        val userResponse: UserResponse? = response.body()


                        // Check if userResponse is not null
                        if (userResponse != null) {

                            // Access the configurations list and iterate through it
                            val configurations: List<ConfigurationResponse> =
                                userResponse.configurations


                            manager.setUsername(userResponse.username);
                            manager.setRole(userResponse.role)
                            manager.setUserID(userResponse.userID)
                            manager.hasUsername();
                            for ((index, configuration) in configurations.withIndex()) {
                                val configurationName: String = configuration.configurationName
                                val value: String = configuration.value

                                val configurationList = ConfigurationList(
                                    configurationId = index + 1, // Use index as configurationId
                                    configurationName = configurationName,
                                    value = value,
                                    userId = userResponse.userID
                                )


                                Thread {
                                    // Run the database insert operation on the new thread
                                    appdatabase.configurationListDao().insert(configurationList)
                                }.start()
                                // Use configurationName and value as needed
                                // For example, print them
                                println("Configuration Name: $configurationName, Value: $value")

                                when (configuration.configurationName) {
                                    "Currency" -> manager.setCreatePDF(configuration.value.toBoolean())
                                    "Language" -> manager.setLanguage(configuration.value)
                                    "BankTransfer" -> manager.setBankTransfer(configuration.value.toBoolean())
                                    "Cash" -> manager.setCash(configuration.value.toBoolean())
                                    "VoucherAccess" -> manager.setVoucherAccess(configuration.value.toBoolean())
                                    "ThemeColor" -> {
                                        val themeColor = getThemeColorByName(configuration.value)
                                        if (themeColor != 0) {
                                            manager.setThemeColor(themeColor)
                                        }
                                    }
                                    "EmailNotifications" -> manager.setEmailNotifications(
                                        configuration.value.toBoolean()
                                    )
                                    "Monetization" -> manager.setMonetization(configuration.value.toBoolean())
                                    // Add more cases for other configuration keys
                                    "MenuList" -> manager.setMenuList(configuration.value)

                                }

                                val username = manager.getUsername();
                                Log.d("usernami ne api", username)
                            }


                        }
                        Log.d("Username", manager.getUsername())

                    } else {
                        // Handle the error response
                        // You can log or display an error message here
                    }

                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    // Handle the API call failure
                    // You can log or display an error message here
                }
            })
        }catch (e: Exception) {
            // Handle errors, e.g., database or SharedPreferences errors
            e.printStackTrace()
        }
    }
}

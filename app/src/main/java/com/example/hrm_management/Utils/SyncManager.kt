package com.example.hrm_management.Utils

import android.content.Context
import android.util.Log
import com.example.hrm_management.AppModule.SharedPreferencesManager
import com.example.hrm_management.Data.Api.Api
import com.example.hrm_management.Data.Api.Model.*
import com.example.hrm_management.Data.Local.AppDatabase
import com.example.hrm_management.Data.Local.ConfigurationList
import com.example.hrm_management.Data.Local.User
import com.example.hrm_management.R
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import javax.inject.Inject
// Inside your SyncManager or Repository class
import retrofit2.HttpException
import retrofit2.await
import java.io.IOException

class SyncManager @Inject constructor(
    private val appdatabase: AppDatabase,
    private val manager: SharedPreferencesManager,
    private val api: Api
) {

    fun sync() {
        try {
            if(manager.isLoggedIn()){
                syncConfigurations(manager.getUsername(), manager.getSession())
            }
        } catch (e: Exception) {
            // Handle errors, e.g., database or SharedPreferences errors
            e.printStackTrace()
        }

        Log.d("From API", manager.getMenuList().toString())
    }



    suspend fun syncUser(username: String, password: String): LoginResponse? {
        try {
            // Show the ProgressBar or handle loading state as needed

            val loginRequest = LoginRequest(password, username, manager.getToken())

            val response = withContext(Dispatchers.IO) {
                try {
                    api.login(loginRequest)
                } catch (e: HttpException) {
                    // Handle HTTP error (e.g., non-2xx response)
                    null
                } catch (e: Exception) {
                    // Handle other exceptions
                    null
                }
            }


            // Hide the ProgressBar or handle loading state as needed

            if (response != null) {
                if (response.isSuccessful) {
                    val loginResponse: LoginResponse? = response.body()
                    if (loginResponse != null) {
                        // Access the configurations list and iterate through it
                        val configurations: List<Configurations> = loginResponse.configurations

                        manager.setUsername(loginResponse.username)
                        manager.setRole(loginResponse.role)
                        manager.setUserID(loginResponse.userID)
                        manager.hasUsername()
                        manager.setToken(loginResponse.fcm_token)
                        manager.setSession(loginResponse.token)

                        for ((index, configuration) in configurations.withIndex()) {
                            val configurationName: String? = configuration.configurationName
                            val value: String = configuration.value

                            // Log each configuration
                            Log.d("LoginActivity", "Configuration $index - Name: $configurationName, Value: $value")

                            val configurationList = ConfigurationList(
                                configurationId = index + 1, // Use index as configurationId
                                configurationName = configurationName,
                                value = value,
                                userId = loginResponse.userID
                            )

                            // Run the database insert operation using Room with coroutines
                            withContext(Dispatchers.IO) {
                                appdatabase.configurationListDao().insert(configurationList)
                            }

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

                            val username = manager.getUsername()
                            Log.d("usernami ne api", username)
                        }

                        // Handle the successful response here
                        manager.setIsLoggedIn(true)
                        Log.d("response", loginResponse.toString())

                        return loginResponse
                    } else {
                        Log.d("response", "Response body is null")
                        manager.setIsLoggedIn(false)
                    }
                } else {
                    Log.d("response", "HTTP Error: ${response.code()}") // Log the HTTP error code
                    manager.setIsLoggedIn(false)
                }
            } else {
                Log.d("response", "Response is null")
                manager.setIsLoggedIn(false)
            }
        } catch (e: Exception) {
            // Handle errors, e.g., database or SharedPreferences errors
            e.printStackTrace()
            FirebaseCrashlytics.getInstance().recordException(e)
        }

        return null
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

    private fun syncConfigurations(username: String, token: String) {
        try {
            val configRequest = ConfigurationRequest(username, token);
            val userrespone: Call<ConfigurationsResponse> = api.getConfigurations(configRequest)

            userrespone.enqueue(object : Callback<ConfigurationsResponse> {
                override fun onResponse(
                    call: Call<ConfigurationsResponse>,
                    response: Response<ConfigurationsResponse>
                ) {
                    if (response.isSuccessful) {
                        val configurationsResponse: ConfigurationsResponse? = response.body()


                        // Check if userResponse is not null
                        if (configurationsResponse != null) {

                            // Access the configurations list and iterate through it
                            val configurations: List<ConfigurationLists> = configurationsResponse.configurations

                            manager.setUsername(configurationsResponse.username);
                            manager.setRole(configurationsResponse.role)
                            manager.setUserID(configurationsResponse.userID)
                            manager.hasUsername();
                            if (configurations != null) {
                                for ((index, configuration) in configurations.withIndex()) {
                                    val configurationName: String = configuration.configurationName
                                    val value: String = configuration.value

                                    val configurationList = ConfigurationList(
                                        configurationId = index + 1, // Use index as configurationId
                                        configurationName = configurationName,
                                        value = value,
                                        userId = configurationsResponse.userID
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


                        }
                        Log.d("Username", manager.getUsername())

                    } else {
                        // Handle the error response
                        // You can log or display an error message here
                    }

                }
                override fun onFailure(call: Call<ConfigurationsResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }catch (e: Exception) {
            // Handle errors, e.g., database or SharedPreferences errors
            e.printStackTrace()
        }
    }


}



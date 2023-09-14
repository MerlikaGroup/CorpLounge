import android.util.Log
import com.example.hrm_management.AppModule.AppConfigurations
import com.example.hrm_management.AppModule.SharedPreferencesManager
import com.example.hrm_management.Data.Local.AppDatabase
import com.example.hrm_management.Data.Local.ConfigurationList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SyncManager @Inject constructor(
    private val appdatabase: AppDatabase,
    private val manager: SharedPreferencesManager
) {

     fun syncConfigurations() {
         try {

             // Perform database or other I/O-bound operations here
             val configurations = appdatabase.configurationListDao().getAllConfigurations()

             for (configuration in configurations) {
                 Log.d("Configurations", configuration.configurationName.toString())
             }

             // Store the retrieved data in SharedPreferences
             for (config in configurations) {
                 when (config.configurationName) {
                     "MenuList" -> config.value?.let { manager.setMenuList(it) }
                     "createPDF" -> manager.setCreatePDF(config.value.toBoolean())
                     // Add more cases for other configuration keys
                 }
             }
         } catch (e: Exception) {
             // Handle errors, e.g., database or SharedPreferences errors
             e.printStackTrace()
         }
     }

         fun insertConfigurationValues() {
                 // Insert configuration values from AppConfigurations into the table
                 appdatabase.configurationListDao().insert(ConfigurationList(1, "MenuList", manager.getMenuList(), 1));
             appdatabase.configurationListDao().insert(ConfigurationList(2, "Currency", manager.getCurrency(), 1));

             appdatabase.configurationListDao().insert(ConfigurationList(3, "Language", manager.getLanguage(), 1));

             appdatabase.configurationListDao().insert(ConfigurationList(4, "BankTransfer", manager.getBankTransfer().toString(), 1));

             appdatabase.configurationListDao().insert(ConfigurationList(5, "Cash", manager.getCash().toString(), 1));

             appdatabase.configurationListDao().insert(ConfigurationList(6, "CreatePDF",manager.getCreatePDF().toString(), 1));



         }
         }

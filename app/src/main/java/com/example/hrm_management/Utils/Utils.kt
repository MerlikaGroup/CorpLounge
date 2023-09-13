import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.LocaleList
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.hrm_management.Views.Register.RegisterFragment
import java.util.*

class Utils {

    // Function to get the Android ID
    @SuppressLint("HardwareIds")
    fun getAndroidID(context: Context): String {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        ) ?: ""
    }

    // Function to get the current locale
    @RequiresApi(Build.VERSION_CODES.N)
    fun getCurrentLocale(context: Context): LocaleList {
        return context.resources.configuration.locales;
    }
}
package com.example.hrm_management.AppModule

import android.content.SharedPreferences

class SharedPreferencesManagerImpl(private val sharedPreferences: SharedPreferences) : SharedPreferencesManager {


    override fun getRole(): Int {
        return sharedPreferences.getInt("ROLE", AppConfigurations.ROLE)
    }

    override fun setRole(role: Int) {
        sharedPreferences.edit().putInt("ROLE", role).apply()
    }

    override fun getUserID(): Int {
        return sharedPreferences.getInt("USERID", AppConfigurations.USERID)
    }

    override fun setUserID(userid: Int) {
        sharedPreferences.edit().putInt("USERID", userid).apply()
    }



    override fun getUsername(): String {
        return sharedPreferences.getString("USERNAME", AppConfigurations.USERNAME) ?: AppConfigurations.USERNAME;
    }

    override fun setUsername(username: String) {
        sharedPreferences.edit().putString("USERNAME", username).apply()

    }

     override fun hasUsername() {
         if(!getUsername().isEmpty()){
             sharedPreferences.edit().putBoolean("HAS_USERNAME", true).apply()
         }
         else{
             sharedPreferences.edit().putBoolean("HAS_USERNAME", false).apply()

         }
     }


    override fun getMenuList(): String {
        return sharedPreferences.getString("MENU_LIST", AppConfigurations.MENU_LIST) ?: AppConfigurations.MENU_LIST
    }

    override fun setMenuList(menuList: String) {
        sharedPreferences.edit().putString("MENU_LIST", menuList).apply()

    }

    override fun getCreatePDF(): Boolean {
        return sharedPreferences.getBoolean("CREATE_PDF", AppConfigurations.CREATE_PDF) ?: AppConfigurations.CREATE_PDF

    }

    override fun setCreatePDF(createPDF: Boolean) {
        sharedPreferences.edit().putBoolean("CREATE_PDF", createPDF).apply()

    }

    override fun getRequestSalaryChange(): Boolean {
        return sharedPreferences.getBoolean("REQUEST_SALARY_CHANGE", AppConfigurations.REQUEST_SALARY_CHANGE) ?: AppConfigurations.REQUEST_SALARY_CHANGE

    }

    override fun setRequestSalaryChange(requestSalaryChange: Boolean) {
        sharedPreferences.edit().putBoolean("REQUEST_SALARY_CHANGE", requestSalaryChange).apply()
    }

    override fun getLanguage(): String {
        return sharedPreferences.getString("LANGUAGE", AppConfigurations.LANGUAGE) ?: AppConfigurations.LANGUAGE

    }

    override fun setLanguage(language: String) {
        sharedPreferences.edit().putString("LANGUAGE", language).apply()
    }

    override fun getVoucherAccess(): Boolean {
        return sharedPreferences.getBoolean("VOUCHER_ACCESS", AppConfigurations.VOUCHER_ACCESS) ?: AppConfigurations.VOUCHER_ACCESS
    }

    override fun setVoucherAccess(voucherAccess: Boolean) {
        sharedPreferences.edit().putBoolean("VOUCHER_ACCESS", voucherAccess).apply()
    }

    override fun getMonetization(): Boolean {
        return sharedPreferences.getBoolean("MONETIZATION", AppConfigurations.MONETIZATION) ?: AppConfigurations.MONETIZATION
    }

    override fun setMonetization(monetization: Boolean) {
        sharedPreferences.edit().putBoolean("MONETIZATION", monetization).apply()
    }

    override fun getThemeColor(): Int {
        return sharedPreferences.getInt("THEME", AppConfigurations.THEME_COLOR) ?: AppConfigurations.THEME_COLOR
    }

    override fun setThemeColor(themeColor: Int) {
        sharedPreferences.edit().putInt("THEME", themeColor).apply()
    }

    override fun getCurrency(): String {
        return sharedPreferences.getString("CURRENCY", AppConfigurations.CURRENCY) ?: AppConfigurations.CURRENCY
    }

    override fun setCurrency(currency: String) {
        sharedPreferences.edit().putString("CURRENCY", currency).apply()
    }

    override fun getEmailNotifications(): Boolean {
        return sharedPreferences.getBoolean("EMAIL_NOTIFICATION", AppConfigurations.EMAIL_NOTIFICATIONS) ?: AppConfigurations.EMAIL_NOTIFICATIONS
    }

    override fun setEmailNotifications(emailNotifications: Boolean) {
        sharedPreferences.edit().putBoolean("EMAIL_NOTIFICATION", emailNotifications).apply()
    }

    override fun getBankTransfer(): Boolean {
        return sharedPreferences.getBoolean("BANK_TRANSFER", AppConfigurations.BANK_TRANSFER) ?: AppConfigurations.BANK_TRANSFER
    }

    override fun setBankTransfer(bankTransfer: Boolean) {
        sharedPreferences.edit().putBoolean("BANK_TRANSFER", bankTransfer).apply()
    }

    override fun getCash(): Boolean {
        return sharedPreferences.getBoolean("CASH", AppConfigurations.CASH) ?: AppConfigurations.CASH
    }

    override fun setCash(cash: Boolean) {
        sharedPreferences.edit().putBoolean("CASH", cash).apply()
    }


}

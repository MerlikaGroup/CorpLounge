package com.example.hrm_management.AppModule

import android.content.SharedPreferences

class SharedPreferencesManagerImpl(private val sharedPreferences: SharedPreferences) : SharedPreferencesManager {

    override fun getMenuList(): String {
        return sharedPreferences.getString("MENU_LIST", AppConfigurations.MENU_LIST) ?: AppConfigurations.MENU_LIST
    }

    override fun setMenuList(menuList: String) {
        sharedPreferences.edit().putString("MENU_LIST", menuList).apply()

    }

    override fun getCreatePDF(): String {
        return sharedPreferences.getString("CREATE_PDF", AppConfigurations.CREATE_PDF) ?: AppConfigurations.CREATE_PDF

    }

    override fun setCreatePDF(createPDF: String) {
        sharedPreferences.edit().putString("CREATE_PDF", createPDF).apply()

    }

    override fun getRequestSalaryChange(): String {
        return sharedPreferences.getString("REQUEST_SALARY_CHANGE", AppConfigurations.REQUEST_SALARY_CHANGE) ?: AppConfigurations.REQUEST_SALARY_CHANGE

    }

    override fun setRequestSalaryChange(requestSalaryChange: String) {
        sharedPreferences.edit().putString("REQUEST_SALARY_CHANGE", requestSalaryChange).apply()
    }

    override fun getLanguage(): String {
        return sharedPreferences.getString("LANGUAGE", AppConfigurations.LANGUAGE) ?: AppConfigurations.LANGUAGE

    }

    override fun setLanguage(language: String) {
        sharedPreferences.edit().putString("LANGUAGE", language).apply()
    }

    override fun getVoucherAccess(): String {
        return sharedPreferences.getString("VOUCHER_ACCESS", AppConfigurations.VOUCHER_ACCESS) ?: AppConfigurations.VOUCHER_ACCESS
    }

    override fun setVoucherAccess(voucherAccess: String) {
        sharedPreferences.edit().putString("VOUCHER_ACCESS", voucherAccess).apply()
    }

    override fun getMonetization(): String {
        return sharedPreferences.getString("MONETIZATION", AppConfigurations.MONETIZATION) ?: AppConfigurations.MONETIZATION
    }

    override fun setMonetization(monetization: String) {
        sharedPreferences.edit().putString("MONETIZATION", monetization).apply()
    }

    override fun getThemeColor(): String {
        return sharedPreferences.getString("THEME", AppConfigurations.THEME_COLOR) ?: AppConfigurations.THEME_COLOR
    }

    override fun setThemeColor(themeColor: String) {
        sharedPreferences.edit().putString("THEME", themeColor).apply()
    }

    override fun getCurrency(): String {
        return sharedPreferences.getString("CURRENCY", AppConfigurations.CURRENCY) ?: AppConfigurations.CURRENCY
    }

    override fun setCurrency(currency: String) {
        sharedPreferences.edit().putString("CURRENCY", currency).apply()
    }

    override fun getEmailNotifications(): String {
        return sharedPreferences.getString("EMAIL_NOTIFICATION", AppConfigurations.EMAIL_NOTIFICATIONS) ?: AppConfigurations.EMAIL_NOTIFICATIONS
    }

    override fun setEmailNotifications(emailNotifications: String) {
        sharedPreferences.edit().putString("EMAIL_NOTIFICATION", emailNotifications).apply()
    }

    override fun getBankTransfer(): String {
        return sharedPreferences.getString("BANK_TRANSFER", AppConfigurations.BANK_TRANSFER) ?: AppConfigurations.BANK_TRANSFER
    }

    override fun setBankTransfer(bankTransfer: String) {
        sharedPreferences.edit().putString("BANK_TRANSFER", bankTransfer).apply()
    }

    override fun getCash(): String {
        return sharedPreferences.getString("CASH", AppConfigurations.CASH) ?: AppConfigurations.CASH
    }

    override fun setCash(cash: String) {
        sharedPreferences.edit().putString("CASH", cash).apply()
    }


}

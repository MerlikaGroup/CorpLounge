package com.example.hrm_management.AppModule

interface SharedPreferencesManager {
    fun getMenuList(): String
    fun setMenuList(menuList: String)

    fun getCreatePDF(): String
    fun setCreatePDF(createPDF: String)

    fun getRequestSalaryChange(): String
    fun setRequestSalaryChange(requestSalaryChange: String)

    fun getLanguage(): String
    fun setLanguage(language: String)

    fun getVoucherAccess(): String
    fun setVoucherAccess(voucherAccess: String)

    fun getMonetization(): String
    fun setMonetization(monetization: String)

    fun getThemeColor(): String
    fun setThemeColor(themeColor: String)

    fun getCurrency(): String
    fun setCurrency(currency: String)

    fun getEmailNotifications(): String
    fun setEmailNotifications(emailNotifications: String)

    fun getBankTransfer(): String
    fun setBankTransfer(bankTransfer: String)

    fun getCash(): String
    fun setCash(cash: String)
}

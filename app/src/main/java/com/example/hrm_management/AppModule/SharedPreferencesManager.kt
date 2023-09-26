package com.example.hrm_management.AppModule

interface SharedPreferencesManager {

    fun hasUsername();

    fun setToken(token: String)
    fun getToken(): String

    fun setSession(token: String)
    fun getSession(): String

    fun isLoggedIn(): Boolean;
    fun setIsLoggedIn(value: Boolean)

    fun getUserID(): Int
    fun setUserID(userID: Int)

    fun getRole(): Int
    fun setRole(role: Int)


    fun getUsername(): String
    fun setUsername(username: String)

    fun getMenuList(): String
    fun setMenuList(menuList: String)

    fun getCreatePDF(): Boolean
    fun setCreatePDF(createPDF: Boolean)

    fun getRequestSalaryChange(): Boolean
    fun setRequestSalaryChange(requestSalaryChange: Boolean)

    fun getLanguage(): String
    fun setLanguage(language: String)

    fun getVoucherAccess(): Boolean
    fun setVoucherAccess(voucherAccess: Boolean)

    fun getMonetization(): Boolean
    fun setMonetization(monetization: Boolean)

    fun getThemeColor(): Int
    fun setThemeColor(themeColor: Int)

    fun getCurrency(): String
    fun setCurrency(currency: String)

    fun getEmailNotifications(): Boolean
    fun setEmailNotifications(emailNotifications: Boolean)

    fun getBankTransfer(): Boolean
    fun setBankTransfer(bankTransfer: Boolean)

    fun getCash(): Boolean
    fun setCash(cash: Boolean)
}

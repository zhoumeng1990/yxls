package com.coder.framework.util

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Zero on 2020/05/30.
 *
 */
class SharedPrefsManager(private val context: Context) {
    companion object {
        const val SP_NAME = "hydf"
        const val TOKEN = "token"
        const val COMPANY_TIME = "company_time"
        const val WELCOME_VERSION = "welcome_version"
        const val VERSION = "version"
        const val PHONE_NUMBER = "phone_number"
        const val H5_VERSION = "h5_version"
    }

    private fun get(): SharedPreferences =
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

//    fun saveTmdbLogged(responseSession: ResponseSession) {
//        get().edit().also { sharedPrefs ->
//            sharedPrefs.putBoolean(TMDB_LOGGED, responseSession.success ?: false)
//            sharedPrefs.putString(TMDB_SESSION_ID, responseSession.sessionId ?: "")
//        }.apply()
//    }

    fun getToken(): String {
        return get().getString(TOKEN, "") ?: ""
    }

    fun saveToken(token: String) {
        get().edit().also { sharedPrefs ->
            sharedPrefs.putString(TOKEN, token)
        }.apply()
    }

    fun getCompanyTime(): String {
        return get().getString(COMPANY_TIME, "") ?: ""
    }

    fun saveCompanyTime(time: String) {
        get().edit().also { sharedPrefs ->
            sharedPrefs.putString(COMPANY_TIME, time)
        }.apply()
    }

    fun getWelcomeVersion(): String {
        return get().getString(WELCOME_VERSION, "") ?: ""
    }

    fun saveWelcomeVersion(time: String) {
        get().edit().also { sharedPrefs ->
            sharedPrefs.putString(WELCOME_VERSION, time)
        }.apply()
    }

    fun getVersion(): String {
        return get().getString(VERSION, "") ?: ""
    }

    fun saveVersion(time: String) {
        get().edit().also { sharedPrefs ->
            sharedPrefs.putString(VERSION, time)
        }.apply()
    }

    fun getPhoneNumber(): String {
        return get().getString(PHONE_NUMBER, "") ?: ""
    }

    fun savePhoneNumber(phone: String) {
        get().edit().also { sharedPrefs ->
            sharedPrefs.putString(PHONE_NUMBER, phone)
        }.apply()
    }

    fun getH5Version(): String {
        return get().getString(H5_VERSION, "") ?: ""
    }

    fun saveH5Version(h5Version: String) {
        get().edit().also { sharedPrefs ->
            sharedPrefs.putString(H5_VERSION, h5Version)
        }.apply()
    }
}
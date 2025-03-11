package com.example.photo.video.ecoraysolutions

import android.content.Context
import android.content.SharedPreferences

class SessionManager(var context: Context) {
    val pref: SharedPreferences = context.getSharedPreferences("ECO_RAY", Context.MODE_PRIVATE)


    fun getPin(): String? {
        return pref.getString("login", "undefined")
    }

    fun setPin(value: String) {
        pref.edit().putString("login", value).apply()
    }

    fun getLogin(): Boolean {
        return pref.getBoolean("isLogin", false)
    }

    fun setLogin(value: Boolean) {
        pref.edit().putBoolean("isLogin", value).apply()
    }

    fun getFirstTime(): Boolean {
        return pref.getBoolean("isFirst", false)
    }

    fun setFirstTime(value: Boolean) {
        pref.edit().putBoolean("isFirst", value).apply()
    }

    fun getUserId(): String? {
        return pref.getString("UserId", "")
    }

    fun setUserId(userId: String) {
        pref.edit().putString("UserId", userId).apply()
    }
}


package com.thakurnitin2684.codeforces.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson


 const val PREFERENCE_NAME = "CODEFORCES_REF"
 const val MAIN_HANDLE = "MAIN_HANDLE"
 const val REMEMBER_ME = "REMEMBER_ME"
 const val VIEW_BOOL = "VIEW_BOOL"

class PrefRepository(val context: Context) {

    private val pref: SharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    private val editor = pref.edit()

    private val gson = Gson()



    private fun String.put(long: Long) {
        editor.putLong(this, long)
        editor.commit()
    }

    private fun String.put(int: Int) {
        editor.putInt(this, int)
        editor.commit()
    }

    private fun String.put(string: String) {
        editor.putString(this, string)
        editor.commit()
    }

    private fun String.put(boolean: Boolean) {
        editor.putBoolean(this, boolean)
        editor.commit()
    }

    private fun String.getLong() = pref.getLong(this, 0)

    private fun String.getInt() = pref.getInt(this, 0)

    private fun String.getString() = pref.getString(this, "")!!

    private fun String.getBoolean(default: Boolean) = pref.getBoolean(this, default)





    fun setMainHandle(handle: String) {
        MAIN_HANDLE.put(handle)
    }

    fun getMainHandle() = MAIN_HANDLE.getString()

    fun setRememberMe(rememberMe: Boolean) {
        REMEMBER_ME.put(rememberMe)
    }

    fun getRememberMe(default: Boolean) = REMEMBER_ME.getBoolean(default)


    fun setViewBool(view: Boolean) {
        VIEW_BOOL.put(view)
    }

    fun getViewBool(default: Boolean) = VIEW_BOOL.getBoolean(default)


    fun clearData() {
        editor.clear()
        editor.commit()
    }
}
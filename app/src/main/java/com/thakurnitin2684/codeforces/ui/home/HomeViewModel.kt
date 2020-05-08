package com.thakurnitin2684.codeforces.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import java.io.IOException
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL

private const val TAG = "HomeViewModel"
class HomeViewModel : ViewModel(){

     var res :String=""
      fun downloadData(vararg params: String?): String {

        Log.d(TAG, params[0]!!)
        if (params[0] == null) {
            return "NO"
        }
        try {
               res= URL(params[0]).readText()
            return res

        } catch (e: Exception) {
            val errorMessage = when (e) {
                is MalformedURLException -> {
                    "downloadData: Invalid URL ${e.message}"
                }
                is IOException -> {
                    "downloadData: IO Exception Reading data ${e.message}"
                }
                is SecurityException -> {
                    "downloadData: Security exception : Needs Permission ${e.message}"
                }
                else -> {
                    "Unknown Error: ${e.message}"
                }
            }
            Log.e(TAG, errorMessage)
            return "NO"
        }
    }

    }

package com.thakurnitin2684.codeforces

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class BaseApplication : Application() {

    private var mainHandle: String = ""
    private var rememberKey: Boolean = false
    private var mobileVw: Boolean = true

    fun getMainHandle(): String {
        return mainHandle
    }

    fun setMainHandle(handle: String) {
        this.mainHandle = handle
    }

    fun getRememberKey(): Boolean {
        return rememberKey
    }

    fun setRememberKey(value: Boolean) {
        this.rememberKey = value
    }

    fun getMobileView(): Boolean {
        return mobileVw
    }

    fun setMobileView(value: Boolean) {
        this.mobileVw = value
    }
}
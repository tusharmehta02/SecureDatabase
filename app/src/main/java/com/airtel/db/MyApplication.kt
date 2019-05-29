package com.airtel.db

import android.app.Application
import com.airtel.securedblibrary.SecuredDataManager

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        System.loadLibrary("native-lib")
        SecuredDataManager.install(this)
    }
}

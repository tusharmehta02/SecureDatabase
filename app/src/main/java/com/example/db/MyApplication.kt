package com.example.db

import android.app.Application
import com.example.mylibrary.SecuredDataManager

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        System.loadLibrary("native-lib")
        SecuredDataManager.install(this)
    }
}

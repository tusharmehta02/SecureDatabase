package com.example.mylibrary

import android.os.Handler

interface SecureDataInteractionManager {
    fun addDataSync(pair: Pair<String, String>)
    fun addDataAsync(pair: Pair<String, String>)
    fun getDataAsync(key: String, handler: Handler, dataLoadListener: DataLoadListener)
    fun getDataSync(key: String): Pair<String, String?>
    fun closeDB()

}
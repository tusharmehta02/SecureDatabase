package com.airtel.securedblibrary

import android.os.Handler
import io.reactivex.Observable

interface SecureDataInteractionManager {
    fun addDataSync(pair: Pair<String, String>)
    fun addDataAsync(pair: Pair<String, String>)
    fun getDataSync(key: String): Pair<String, String?>
    fun deleteDataSync(key: String): Boolean
    fun observeData(key: String): Observable<Pair<String, String?>>
    fun closeDB()

}
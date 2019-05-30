package com.airtel.securedblibrary

import android.content.Context
import net.sqlcipher.database.SQLiteDatabase

object SecuredDataManager {

    fun install(context: Context) {
        SQLiteDatabase.loadLibs(context)
    }

} 
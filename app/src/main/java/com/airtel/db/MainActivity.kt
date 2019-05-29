package com.airtel.db

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.airtel.securedblibrary.DBHelper
import com.airtel.securedblibrary.SecureDataInteractionManager

class MainActivity : AppCompatActivity() {
    lateinit var secureDataInteractionManager: SecureDataInteractionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        secureDataInteractionManager = DBHelper.getInstance(this, stringFromJNIClass())
        secureDataInteractionManager.addDataSync(Pair("10","tus"))
        val pair  = secureDataInteractionManager.getDataSync("10")
    }

    external fun stringFromJNIClass(): String

}

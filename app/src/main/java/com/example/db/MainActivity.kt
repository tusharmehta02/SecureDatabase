package com.example.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.mylibrary.DBHelper
import com.example.mylibrary.SecureDataInteractionManager

class MainActivity : AppCompatActivity() {
    lateinit var secureDataInteractionManager: SecureDataInteractionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        secureDataInteractionManager = DBHelper.getInstance(this, stringFromJNIClass())
//        secureDataInteractionManager.addDataSync(Pair("10","tus"))
        val pair  = secureDataInteractionManager.getDataSync("10")
        Log.e("test", pair.first+" "+pair.second)


    }

    external fun stringFromJNIClass(): String

}

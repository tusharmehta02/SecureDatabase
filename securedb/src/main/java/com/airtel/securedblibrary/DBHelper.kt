package com.airtel.securedblibrary

import android.content.ContentValues
import android.content.Context
import android.os.Handler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SQLiteOpenHelper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class DBHelper private constructor(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version), SecureDataInteractionManager {
    var executorService: ExecutorService = Executors.newFixedThreadPool(5)
    var dataLoadListener: DataLoadListener? = null

    init {
        Companion.context = context
    }

    override fun closeDB() {
        db?.close()
    }

    override fun addDataSync(pair: Pair<String, String>) {
        insertData(pair)
    }

    override fun addDataAsync(pair: Pair<String, String>) {
        executorService.execute {
            insertData(pair)
        }
        executorService.shutdown()
    }

    override fun deleteDataSync(key: String): Boolean{
        return deleteData(key)
    }

    override fun getDataSync(key: String): Pair<String, String?> {
        return getValue(key)
    }

    override fun observeData(key: String): Observable<Pair<String, String?>> {
        val res: BehaviorSubject<Pair<String, String?>> =  BehaviorSubject.create()
        executorService.execute { getValue(res,key) }
        return res
    }

    private fun getValue(res: BehaviorSubject<Pair<String, String?>>,key:String) {
        res.onNext(getValue(key))
    }


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table $SAMPLE_TABLE_NAME($KEY_DATA TEXT, $KEY_VALUE TEXT)")
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onCreate(db)
    }

    fun insertData(pair: Pair<String, String>): Long {
        val values = ContentValues()
        values.put(KEY_DATA, pair.first)
        values.put(KEY_VALUE, pair.second)

        // insert row
        return db!!.insert(SAMPLE_TABLE_NAME, null, values)
    }

    fun getValue(key: String): Pair<String, String?> {
        val selectQuery = "SELECT  * FROM $SAMPLE_TABLE_NAME where $KEY_DATA = '$key'"
        var value: String? = null
        val c = db!!.rawQuery(selectQuery, null)
        if (c.moveToFirst()) {
            do {
                value = c.getString(c.getColumnIndex(KEY_VALUE))
            } while (c.moveToNext())
        }
        val pair = Pair(key, value)
        return pair
    }

    fun deleteData(key: String): Boolean {
        return db!!.delete(SAMPLE_TABLE_NAME, KEY_DATA + "=" + key, null) > 0;
    }


    companion object {
        private val DATABASE_VERSION = 1
        private var db: SQLiteDatabase? = null
        private var instance: DBHelper? = null
        private var context: Context? = null
        val DATABASE_NAME = "Sample.db"
        val SAMPLE_TABLE_NAME = "sample_table"
        val KEY_DATA = "data"
        val KEY_VALUE = "value"


        @Synchronized
        fun getInstance(context: Context, password: String): SecureDataInteractionManager {
            if (instance == null) {
                instance = DBHelper(
                    context,
                    DATABASE_NAME,
                    null,
                    DATABASE_VERSION
                )
                db = instance?.getWritableDatabase(password)

            }
            return instance as DBHelper
        }
    }
}
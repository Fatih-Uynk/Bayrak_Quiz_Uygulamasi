package com.fatihuynk.bayrak_quiz_uygulamasi


import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


class DatabaseCopyHelper(private val myContext: Context) : SQLiteOpenHelper(myContext, DB_NAME, null, 1) {

    private val DB_PATH = "/data/data/" + myContext.packageName + "/databases/"
    private var myDataBase: SQLiteDatabase? = null

    @Throws(IOException::class)
    fun createDataBase() {
        val dbExists = checkDataBase()

        if (!dbExists) {
            this.readableDatabase.close() // Boş veritabanını oluşturup kapatıyoruz

            try {
                copyDataBase()
            } catch (e: IOException) {
                throw Error("Error copying database")
            }
        }
    }

    private fun checkDataBase(): Boolean {
        var checkDB: SQLiteDatabase? = null
        try {
            val myPath = DB_PATH + DB_NAME
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)
        } catch (e: SQLiteException) {
            // Veritabanı mevcut değil
        }

        checkDB?.close()
        return checkDB != null
    }

    @Throws(IOException::class)
    private fun copyDataBase() {
        val myInput = myContext.assets.open(DB_NAME)
        val outFileName = DB_PATH + DB_NAME

        val myOutput = FileOutputStream(outFileName)

        val buffer = ByteArray(1024)
        var length: Int
        while (myInput.read(buffer).also { length = it } > 0) {
            myOutput.write(buffer, 0, length)
        }

        myOutput.flush()
        myOutput.close()
        myInput.close()
    }

    @Throws(SQLException::class)
    fun openDataBase() {
        val myPath = DB_PATH + DB_NAME
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)
    }

    @Synchronized
    override fun close() {
        myDataBase?.close()
        super.close()
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Yeni veritabanı oluşturulurken çalışır
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Veritabanı yükseltme işlemleri
    }

    companion object {
        private const val DB_NAME = "bayraklar.sqlite" // Veritabanı dosyanızın adı
    }

    fun copyDatabaseIfNeeded() {
        try {
            createDataBase()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

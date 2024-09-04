package com.fatihuynk.bayrak_quiz_uygulamasi

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class VeriTabaniYardimcisi(context: Context) :
    SQLiteOpenHelper(context, "bayraklar.sqlite", null, 1) {

    //Veritabanı üzerinde ki tabloları tanımladığımız yer
    override fun onCreate(db: SQLiteDatabase?) {
        //Bu ifade null olabilir o yüzden soru işareti koyduğumuz da kontrollü bir şekilde kullanabiliriz
        db?.execSQL("CREATE TABLE IF NOT EXISTS bayraklar (bayrak_id INTEGER PRIMARY KEY AUTOINCREMENT, bayrak_ad TEXT, bayrak_resim TEXT);")
    }

    //Tablo da bir sıkıntı oluştuğu zaman tabloyu silip yeniden oluşturur
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS bayraklar")

        onCreate(db)
    }
}
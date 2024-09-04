package com.fatihuynk.bayrak_quiz_uygulamasi

class bayraklardao {

    //5 tane bayrak kaydı getirecek bu fonksiyon ve arrayList in içine aktarılacak.
    fun rastgele5BayrakGetir(vt: VeriTabaniYardimcisi): ArrayList<Bayraklar> {

        val bayraklarListe =
            ArrayList<Bayraklar>() //ArrayList oluşturduk,kayıtları burada toplamış olacağız
        val db = vt.writableDatabase
        val c = db.rawQuery("SELECT * FROM bayraklar ORDER BY RANDOM() LIMIT 5", null)

        while (c.moveToNext()) {
            val bayrak = Bayraklar(
                c.getInt(c.getColumnIndexOrThrow("bayrak_id")),
                c.getString(c.getColumnIndexOrThrow("bayrak_ad")),
                c.getString(c.getColumnIndexOrThrow("bayrak_resim"))
            )
            bayraklarListe.add(bayrak)
        }

        return bayraklarListe
    }


    fun rastgele3YanlisSecenekGetir(vt: VeriTabaniYardimcisi, bayrak_id: Int): ArrayList<Bayraklar> {

        val bayraklarListe = ArrayList<Bayraklar>()
        val db = vt.writableDatabase
        val c = db.rawQuery("SELECT * FROM bayraklar WHERE bayrak_id != $bayrak_id ORDER BY RANDOM() LIMIT 3", null)

        while (c.moveToNext()) {
            val bayrak = Bayraklar(
                c.getInt(c.getColumnIndexOrThrow("bayrak_id")),
                c.getString(c.getColumnIndexOrThrow("bayrak_ad")),
                c.getString(c.getColumnIndexOrThrow("bayrak_resim"))
            )
            bayraklarListe.add(bayrak)
        }

        return bayraklarListe
    }
}
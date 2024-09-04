package com.fatihuynk.bayrak_quiz_uygulamasi

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fatihuynk.bayrak_quiz_uygulamasi.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        veritabaniKopyala()

        binding.buttonBasla.setOnClickListener {
            //Sayfalar arasında geçiş yapmak için
            startActivity(Intent(this@MainActivity, QuizActivity::class.java))

            finish()
        }
    }

    fun veritabaniKopyala() {

        //Nesne oluşturduk
        val copyHelper = DatabaseCopyHelper(this)

        try {
            copyHelper.createDataBase()
            copyHelper.openDataBase()

        } catch (e: Exception) {
            e.printStackTrace()//Hata çıktısını alacağız
        }
    }
}
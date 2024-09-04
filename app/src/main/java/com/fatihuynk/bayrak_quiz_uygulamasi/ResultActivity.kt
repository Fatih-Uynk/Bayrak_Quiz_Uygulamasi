package com.fatihuynk.bayrak_quiz_uygulamasi

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fatihuynk.bayrak_quiz_uygulamasi.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //QuizActivity den gönderdiğimiz dogruSayac da ki veri transferini burda yakaladık
        val dogruSayac = intent.getIntExtra("dogruSayac",0)

        binding.textViewSonuc.text = "$dogruSayac DOĞRU ${5 - dogruSayac} YANLIŞ"

        binding.textViewYuzdeSonuc.text = "% ${(dogruSayac * 100) / 5} BAŞARI"


        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.buttonTekrar.setOnClickListener {

            startActivity(Intent(this@ResultActivity, QuizActivity::class.java))
            finish()
        }
    }
}
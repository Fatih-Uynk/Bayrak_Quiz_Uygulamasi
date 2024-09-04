package com.fatihuynk.bayrak_quiz_uygulamasi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fatihuynk.bayrak_quiz_uygulamasi.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizBinding

    private lateinit var sorular: ArrayList<Bayraklar>//5 tane soruyu getiren ArrayList
    private lateinit var yanlisSecenekler: ArrayList<Bayraklar>
    private lateinit var dogruSoru: Bayraklar
    private lateinit var tumSecenekler: HashSet<Bayraklar>
    private lateinit var vt: VeriTabaniYardimcisi

    private var soruSayac = 0
    private var dogruSayac = 0
    private var yanlisSayac = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vt = VeriTabaniYardimcisi(this)

        sorular = bayraklardao().rastgele5BayrakGetir(vt)

        soruYukle()


        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.buttonA.setOnClickListener {
            dogruKontrol(binding.buttonA)
            soruSayacKontrol()
        }

        binding.buttonB.setOnClickListener{
            dogruKontrol(binding.buttonB)
            soruSayacKontrol()
        }

        binding.buttonC.setOnClickListener {
            dogruKontrol(binding.buttonC)
            soruSayacKontrol()
        }

        binding.buttonD.setOnClickListener {
            dogruKontrol(binding.buttonD)
            soruSayacKontrol()
        }
    }

    fun soruYukle() {
        binding.textViewSoruSayi.text = "${soruSayac + 1}. Soru"

        //Doğru soruya sırayla erişmiş oluruz
        dogruSoru = sorular.get(soruSayac)

        //Resmi dinamik olarak yüklemek için
        binding.imageViewBayrak.setImageResource(resources.getIdentifier(dogruSoru.bayrak_resim,"drawable",packageName))

        yanlisSecenekler = bayraklardao().rastgele3YanlisSecenekGetir(vt, dogruSoru.bayrak_id)

        tumSecenekler = HashSet() //HashSet() yerlerini karıştıracak
        tumSecenekler.add(dogruSoru)//Toplam da doğru seçenekle birlikte 4 tane seçenek olmuş oldu
        tumSecenekler.add(yanlisSecenekler.get(0))//0. veriyi yani ilk veriyi yerleştirdik
        tumSecenekler.add(yanlisSecenekler.get(1))//3 tane yanlış cevap olacak ve o yüzden 3 tane yazdık
        tumSecenekler.add(yanlisSecenekler.get(2))

        //HashSet() içine yerleştirdiğimiz verileri rastgele olarak butonların üzerine aktardık
        binding.buttonA.text = tumSecenekler.elementAt(0).bayrak_ad
        binding.buttonB.text = tumSecenekler.elementAt(1).bayrak_ad
        binding.buttonC.text = tumSecenekler.elementAt(2).bayrak_ad
        binding.buttonD.text = tumSecenekler.elementAt(3).bayrak_ad
    }

    fun soruSayacKontrol(){
        soruSayac++

        if (soruSayac != 5){
            soruYukle()

        }else{//Sayfalar arası geçiş yaparken veri transferi yaptık
            val intent = Intent(this@QuizActivity, ResultActivity::class.java)
            intent.putExtra("dogruSayac",dogruSayac)
            startActivity(intent)
            finish()
        }
    }
    //Buton sınıfından bir nesne oluşturduk, butonun üzerinde ki yazıyı doğru cevapla kıyaslayacağız.
    //ve doğru mu yanlış mı olduğunu anlamaya çalışacağız
    fun dogruKontrol(button: Button){
        val buttonYazi = button.text.toString()// diyerek üzerinde ki yazıyı almış oluruz
        val dogruCevap = dogruSoru.bayrak_ad //Doğru sorunun bayrak adına erişmiş oluruz

        //Kıyaslama yapacağız
        if (buttonYazi == dogruCevap){
            dogruSayac++

        }else{
            yanlisSayac++
        }

        binding.textViewDogru.text = "Doğru : $dogruSayac"
        binding.textViewYanlis.text = "Yanlış : $yanlisSayac"
    }
}
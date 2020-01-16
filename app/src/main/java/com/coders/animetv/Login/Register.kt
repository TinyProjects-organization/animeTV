package com.coders.animetv.Login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.coders.animetv.Homescreen.HomeScreen
import com.coders.animetv.Models.Users
import com.coders.animetv.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_register.*
import java.text.DateFormat
import java.util.*


class Register : AppCompatActivity() {

    // firebase kısmı tanımlama
    lateinit var mAuth: FirebaseAuth
    lateinit var mRef: DatabaseReference
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener

    //progresBar
    lateinit var progressBar: ProgressBar

    // anlık saati ve günü oluşturan sistem //
    val currentTime: Date = Calendar.getInstance().getTime()
    val currentTimeString: String = DateFormat.getTimeInstance().format(currentTime)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //basit watcher işlemleri ve buton işlemleri
        init()

        // Firebase kayıt olma ve kontrol işlemleri
        register()

        //hesaba giriş yapılmış mı yapılmamış mı diye
        firebaseAuthListener()

        //barı progrese eşitleme
        progressBar = progressBarRegister

        // firebase tanımlamarı atama  kısmı //
        mAuth = FirebaseAuth.getInstance()
        mRef = FirebaseDatabase.getInstance().reference
        // firebase tanımlamarı atama  kısmı son //

    }

    private fun init() {
        /* Register den Logine geri dönüş */
        backtologin.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        // Register den Logine geri dönüş son*/


        // Register sayfasındaki show password kısmı */
        showHidePassCheckRegister.setOnClickListener {
            if (showHidePassCheckRegister.isChecked) {
                passwordInputRegister.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            } else {
                passwordInputRegister.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            }
        }
        //Register sayfasındaki show password kısmı son //

/*
        ///Register sayfasından email kodu kontrol sayfasına gider //
        registerBtn.setOnClickListener{
            RegisterPageRoot.visibility = View.GONE
            RegisterPageContainer.visibility = View.VISIBLE
            var transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.RegisterPageContainer,EMailKodragment())
            transaction.addToBackStack(null)
            transaction.commit()
              //event bus ile girilen emaili alıp fragmente gönderme //
            EventBus.getDefault().postSticky(EventBusData.EMailGonder(eMailInputRegister.text.toString()))
        }

 */

        //Register sayfasından email kodu kontrol sayfasına gider son //


        // girelen veriyi takip edip belli kurala göre onaylama satır 110//
        eMailInputRegister.addTextChangedListener(watcher)
        userNameInputRegister.addTextChangedListener(watcher)
        passwordInputRegister.addTextChangedListener(watcher)
        // girelen veriyi takip edip belli kurala göre onaylama son//

        //auth kısmına veri ekleme firebase //

    }

    //Girelen input kontrol panali //
    private val watcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            if (s?.length == 0) {
                registerBtn.setBackgroundResource(R.drawable.bg_button_empty)
            }
        }


        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s!!.length >= 1) {
                if (passwordInputRegister.text.toString().length > 5
                    && userNameInputRegister.text.toString().length > 5
                    && eMailInputRegister.text.toString().length > 5
                ) {
                    registerBtn.setBackgroundResource(R.drawable.bg_button_success)
                    registerBtn.setTextColor(Color.rgb(56, 211, 58))
                    registerBtn.isEnabled = true

                } else {
                    //buraya girilmsine rağmen tamamını karşılamayınca yapılıcaklar
                    registerBtn.setBackgroundResource(R.drawable.bg_button_fail)
                    registerBtn.setTextColor(Color.rgb(245, 57, 57))
                    registerBtn.isEnabled = false
                }

            } else {
                //buraya hiçbirşey girilmediğinde yapılıcaklar
                registerBtn.setBackgroundResource(R.drawable.bg_button_empty)
                registerBtn.setTextColor(Color.WHITE)
                registerBtn.isEnabled = false

            }
        }
    }
    //Girelen input kontrol panali son//

    /////////    Register auth kısmına veri ekleme firebase  ////////////
    fun register() {
        registerBtn.setOnClickListener {
            var email = eMailInputRegister.text.toString()
            var password = passwordInputRegister.text.toString()
            var userNickname = userNameInputRegister.text.toString()
            // kontrol sonrası olur ise yapılacaklar için onay kodu
            var userInfoExistanceCheck = false

            // Firebase Veri çekip kontrol kısmı   //
            mRef.child("users").child("typeC")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        //eğer snaphot yani DB anlık hali boş değilse
                        if (p0.value != null) {
                            for (user in p0.children) {
                                var gelenKullanicilar = user.getValue(Users::class.java)

                                //DB deki kayıtlı emailleri kontrol ediyor ki aynısı açılmasın
                                if (gelenKullanicilar!!.user_email == eMailInputRegister.text.toString()) {

                                    // Buraya email var ise yapılacaklar yazılacak  EGEMEN ALTAŞ//
                                    Toast.makeText(
                                        applicationContext,
                                        "Bu eposta zaten var",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    //eğer hiçbiri yoksa true yapsın ////
                                    userInfoExistanceCheck = true
                                    break
                                }
                                // DB deki kullanıcıların nicklerini kontrol ediyor
                                else if (gelenKullanicilar.user_nickname == userNameInputRegister.text.toString()) {
                                    // Buraya username var ise yapılacaklar yazılacak  EGEMEN ALTAŞ//
                                    Toast.makeText(
                                        applicationContext,
                                        "Bu kullanici adi zaten var",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    //eğer hiçbiri yok uygun ise true yapsın //////
                                    userInfoExistanceCheck = true
                                    break
                                }
                            }
                        }
                        // DB kontrolu sonucu uygun ise buraya Adım adım yeni veri yazma //
                        if (userInfoExistanceCheck == false) {

                            //Butona tıklanıldığı gibi görünür olup dönmeye başlar ///
                            progressBar.visibility = View.VISIBLE
                            //Butona tıklanıldığı gibi görünür olup dönmeye başlar SON///

                            mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { p0 ->
                                    if (p0.isComplete) {
                                        val user_id = mAuth.currentUser!!.uid

                                        //oturum açan kullanıcının verilerini DB ye kaydetme //
                                        val kaydedilicekKullanici =
                                            Users(
                                                email,
                                                password,
                                                userNickname,
                                                user_id,
                                                currentTimeString
                                            )
                                        // DB oluşturma ağacı ////////
                                        mRef.child("users").child("typeC").child(user_id)
                                            .setValue(kaydedilicekKullanici)
                                            .addOnCompleteListener { reg ->
                                                if (reg.isSuccessful) {
                                                    //// eğer başarılı bir şekilde DB ye yazarsa ana ekrana geçsin HomeScreene geçer ///
                                                    val intent = Intent(
                                                        applicationContext,
                                                        HomeScreen::class.java
                                                    ).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                                                    startActivity(intent)
                                                    //// eğer başarılı bir şekilde DB ye yazarsa ana ekrana geçsin HomeScreene geçer SON///

                                                    //eğer giriş yapılmış ise bu sayfaya geri dönülmez //
                                                    finish()
                                                    //eğer giriş yapılmış ise bu sayfaya geri dönülmez SON//

                                                    //giriş yapılırken ana sayfaya progres bar görünmez olur //
                                                    progressBar.visibility = View.INVISIBLE
                                                    //giriş yapılırken ana sayfaya progres bar görünmez olur SON//
                                                } else {
                                                    //eğer kullanıcıyı DB ye yazarken sorun oluşursa kullanıcıyı silme kısmı //

                                                    mAuth.currentUser!!.delete()
                                                        .addOnCompleteListener { p1 ->
                                                            if (p1.isSuccessful) {

                                                                //hata oluşur ise progress bar kaybolup toast msg yazıcak //
                                                                progressBar.visibility =
                                                                    View.INVISIBLE
                                                                Toast.makeText(
                                                                    applicationContext,
                                                                    "Bir hata oluştu, Lütfen tekrar deneyiniz",
                                                                    Toast.LENGTH_LONG
                                                                ).show()
                                                            }
                                                        }
                                                    //eğer kullanıcıyı DB ye yazarken sorun oluşursa kullanıcıyı silme kısmı son//
                                                }
                                            }
                                        //oturum açan kullanıcının verilerini DB ye kaydetme son///

                                    } else {
                                        progressBar.visibility = View.INVISIBLE
                                        Toast.makeText(
                                            applicationContext,
                                            "Problem oluştu",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                        }
                        // DB kontrolu sonucu uygun ise buraya Adım adım yeni veri yazma SON //
                    }
                })
            // Firebase Veri çekip kontrol kısmı  SON //
        }
    }
    ////////Register  auth kısmına veri ekleme firebase SON ///////////

    // kullanıcı giriş yapmış ve çıkış yapmadıysa otomatik giriş gibi işlemler //
    private fun firebaseAuthListener() {
        mAuthListener = object : FirebaseAuth.AuthStateListener {
            override fun onAuthStateChanged(p0: FirebaseAuth) {
                var user = FirebaseAuth.getInstance().currentUser
                // eğer kullanıcı bir dafa giriş yapmış ise çıkış yapana kadar otomatik sisteme sokar //
                if (user != null) {
                    var intent = Intent(
                        applicationContext,
                        HomeScreen::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    // eğer kullanıcı bir dafa giriş yapmış ise çıkış yapana kadar otomatik sisteme sokar SON//

                    // eğer kullanıcı geri butonuna basar ise bu activityi geçtiği için geri dönemicek //
                    finish()
                    // eğer kullanıcı geri butonuna basar ise bu activityi geçtiği için geri dönemicek SON//

                } else {
                }
            }
        }
    }
    // kullanıcı giriş yapmış ve çıkış yapmadıysa otomatik giriş gibi işlemler SON//

    // Login sayfasının activity kısmı çalışmaya başladığında ve durdurulduğunda yapılıcaklar //
    override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener(mAuthListener)
    }

    override fun onStop() {
        super.onStop()
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener)
        }
    }
    // Login sayfasının activity kısmı çalışmaya başladığında ve durdurulduğunda yapılıcaklar SON//

}

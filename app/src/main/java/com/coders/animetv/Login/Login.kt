package com.coders.animetv.Login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import com.coders.animetv.Homescreen.HomeScreen
import com.coders.animetv.Models.Users
import com.coders.animetv.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    // firebase kısmı tanımlama
    lateinit var mAuth: FirebaseAuth
    lateinit var mRef: DatabaseReference
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // kullanıcı giriş yapmış ve çıkış yapmadıysa otomatik giriş gibi işlemler
        firebaseAuthListener()

        // firebase tanımlamarı atama  kısmı //
        mAuth = FirebaseAuth.getInstance()
        mRef = FirebaseDatabase.getInstance().reference
        // firebase tanımlamarı atama  kısmı son //

        // basit şifre gösterme watcher gibi işlemler var
        init()

        // giriş yapma işlemleri var
        loginViaFirebase()



    }

    // Firebase giriş ekranındaki giriş işlemleri kısmı //
    private fun loginViaFirebase() {
        signinBtn.setOnClickListener {
            dBLoginInfoCheck(
                usernameInput.text.toString(),
                passwordInput.text.toString()
            )

        }
    }

    // firebaseLogin fonksiyonunki DBLoginInfoCheck fundan buraya gelir bilgiler //
    private fun dBLoginInfoCheck(eMailOrUserName: String, pass: String) {
        // öyle bir kullanıcı var mı yok mu diye kontrol //
        var kullaniciBulundu = false
        // öyle bir kullanıcı var mı yok mu diye kontrol SON//

        mRef.child("users").child("typeC").orderByChild("user_email")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                // bu Fonksiyonun içinde verilere tek tek bakıp o kullanıcı adını yada email var mı diye arıyor //
                override fun onDataChange(p0: DataSnapshot) {
                    for (db in p0.children) {
                        val bulunanKullanici = db.getValue(Users::class.java)
                        if (bulunanKullanici!!.user_email.toString() == eMailOrUserName) {
                            oturumAC(bulunanKullanici, pass)
                            kullaniciBulundu = true
                            break
                        } else if (bulunanKullanici.user_nickname.toString() == eMailOrUserName) {
                            oturumAC(bulunanKullanici, pass)
                            kullaniciBulundu = true
                            break
                        }
                    }
                    // eğer böyle bir kullanıcı bulunmaz ise //
                    if (!kullaniciBulundu) {
                        Toast.makeText(
                            applicationContext,
                            "Kullanıcı bulunamadı",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    // eğer böyle bir kullanıcı bulunmaz ise //
                }
                // bu Fonksiyonun içinde verilere tek tek bakıp o kullanıcı adını yada email var mı diye arıyor SON//
            })
    }

    // bulunan kullanicinin bilgileri ile girilen bilgileri eşitleyip girmek //
    private fun oturumAC(bulunanKullanici: Users, pass: String) {
        // kullanıcı ne ile girerse girsin bilgisindeki emaili boşa atayıp onla giriş yapıyoruz //
        val geciciEPOSTA: String = bulunanKullanici.user_email.toString()
        // kullanıcı ne ile girerse girsin bilgisindeki emaili boşa atayıp onla giriş yapıyoruz SON//
        // Firebase Auth kısmı //
        mAuth.signInWithEmailAndPassword(geciciEPOSTA, pass)
            .addOnCompleteListener { p0 ->
                if (p0.isSuccessful) {
                    val intent = Intent(applicationContext, HomeScreen::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                } else {
                    // eğer 2 bilgiden biri yanlış bile olsa buraya girer //
                    Toast.makeText(
                        applicationContext,
                        "Kullanıcı adi yada şifre hatalı",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
    // bulunan kullanicinin bilgileri ile girilen bilgileri eşitleyip girmek SON//
    // firebaseLogin fonksiyonunki DBLoginInfoCheck fundan buraya gelir bilgiler SON//
    // Firebase giriş ekranındaki giriş işlemleri kısmı SON//

    //basit işlemlerin tanımlanması //
    private fun init() {

        /* Login den register sayfasona geçiş INTENT */
        createAnAccount.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        /* Login sayfasındaki show password kısmı */
        showHidePassCheck.setOnClickListener {
            if (showHidePassCheck.isChecked) {
                passwordInput.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                passwordInput.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        /* Login den forgot password sayfasona geçiş INTENT */
        forgotPasswordTextView.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
        // satır 54 deki izleyicinin tanımlanma kısmı
        usernameInput.addTextChangedListener(watcher)
        passwordInput.addTextChangedListener(watcher)
    }
//basit işlemlerin tanımlanması SON//

    // Login kısmındaki bilgileri doğru girildiğinde aktifleşicek btn izleyici kısmı //
    private val watcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s!!.length >= 5) {
                if (usernameInput.text.toString().length > 5 && passwordInput.text.toString().length > 5) {
                    signinBtn.setBackgroundResource(R.drawable.bg_button_success)
                    signinBtn.setTextColor(Color.rgb(56, 211, 58))
                    signinBtn.isEnabled = true
                } else {
                    // Giriş ekranındaki verileri eksik girildiğinde //
                    signinBtn.setBackgroundResource(R.drawable.bg_button_fail)
                    signinBtn.setTextColor(Color.rgb(245, 57, 57))
                    signinBtn.isEnabled = false
                }
            } else {
                // Giriş ekranında buraya hiçbirşey girilmediğinde yapılıcaklar //
                signinBtn.setBackgroundResource(R.drawable.bg_button_empty)
                signinBtn.setTextColor(Color.WHITE)
                signinBtn.isEnabled = false
            }
        }

    }
    // Login kısmındaki bilgileri doğru girildiğinde aktifleşicek btn izleyici kısmı son //


    // kullanıcı giriş yapmış ve çıkış yapmadıysa otomatik giriş gibi işlemler //
    private fun firebaseAuthListener() {
        mAuthListener = FirebaseAuth.AuthStateListener {
            val user = FirebaseAuth.getInstance().currentUser
            // eğer kullanıcı bir dafa giriş yapmış ise çıkış yapana kadar otomatik sisteme sokar //
            if (user != null) {
                val intent = Intent(
                    applicationContext,
                    HomeScreen::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                // eğer kullanıcı bir dafa giriş yapmış ise çıkış yapana kadar otomatik sisteme sokar SON//

                // eğer kullanıcı geri butonuna basar ise bu activityi geçtiği için geri dönemicek //
                finish()
                // eğer kullanıcı geri butonuna basar ise bu activityi geçtiği için geri dönemicek SON//

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
        mAuth.removeAuthStateListener(mAuthListener)
    }
    // Login sayfasının activity kısmı çalışmaya başladığında ve durdurulduğunda yapılıcaklar SON//

}
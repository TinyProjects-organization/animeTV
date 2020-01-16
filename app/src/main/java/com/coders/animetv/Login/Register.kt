package com.coders.animetv.Login

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import com.coders.animetv.Homescreen.HomeScreen
import com.coders.animetv.Models.Users
import com.coders.animetv.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    // firebase kısmı
    lateinit var mAuth: FirebaseAuth
    lateinit var mRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
        register()


        // firebase kayıt kısmı //
        mAuth = FirebaseAuth.getInstance()
        mRef = FirebaseDatabase.getInstance().reference
        // firebase kayıt kısmı son //

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


        // girelen veriyi takip edip belli kurala göre onaylama //
        eMailInputRegister.addTextChangedListener(watcher)
        userNameInputRegister.addTextChangedListener(watcher)
        passwordInputRegister.addTextChangedListener(watcher)
        // girelen veriyi takip edip belli kurala göre onaylama son//

        //auth kısmına veri ekleme firebase //

    }


    // geri tuşuna basılınca geri gideni geri getirir     //
    override fun onBackPressed() {
        RegisterPageRoot.visibility = View.VISIBLE
        super.onBackPressed()
    }
    //geri tuşuna basılınca geri gideni geri getirir  son//


    //Girelen input kontrol panali //
    val watcher: TextWatcher = object : TextWatcher {
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
            var user_nickname = userNameInputRegister.text.toString()

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { p0 ->
                if (p0.isComplete) {
                    var user_id = mAuth.currentUser!!.uid
                    //oturum açan kullanıcının verilerini DB ye kaydetme //
                    var kaydedilicekKullanıcı = Users(email, password, user_nickname, user_id)
                    mRef.child("users").child("typeC").child(user_id)
                        .setValue(kaydedilicekKullanıcı)
                        .addOnCompleteListener { reg ->
                            if (reg.isSuccessful) {
                                //eğer başarılı bir şekilde DB ye yazarsa ana ekrana geçsin
                                Toast.makeText(
                                    applicationContext,
                                    "Kayıt oluşturuldu Giriş yapılıyor",
                                    Toast.LENGTH_LONG
                                ).show()
                                val intent = Intent(this, HomeScreen::class.java)
                                startActivity(intent)

                            } else {
                                //eğer kullanıcıyı DB ye yazarken sorun oluşursa kullanıcıyı silme kısmı //
                                mAuth.currentUser!!.delete()
                                    .addOnCompleteListener { p1 ->
                                        if (p1.isSuccessful) {
                                            Toast.makeText(
                                                applicationContext,
                                                "Bir hata oluştu, Tekrar deneyiniz",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                //eğer kullanıcıyı DB ye yazarken sorun oluşursa kullanıcıyı silme kısmı son//

                            }
                        }
                    //oturum açan kullanıcının verilerini DB ye kaydetme son///


                } else {

                    Toast.makeText(applicationContext, "Problem oluştu", Toast.LENGTH_LONG).show()

                }
            }

        }
    }
    ////////Register  auth kısmına veri ekleme firebase son ///////////


}

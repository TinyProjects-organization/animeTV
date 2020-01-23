package com.coders.animetv.Login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.coders.animetv.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {
    // firebase kısmı tanımlama
    lateinit var mAuth: FirebaseAuth
    lateinit var mRef: DatabaseReference
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // firebase tanımlamarı atama  kısmı //
        mAuth = FirebaseAuth.getInstance()
        mRef = FirebaseDatabase.getInstance().reference
        // firebase tanımlamarı atama  kısmı son //

        // basit watcher olayları
        init()


    }



    private fun init() {
        // Forget pass sayfasından login sayfasına intent  //
        forgetPassToLogin.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        // Forget pass sayfasından login sayfasına intent  SON//

        // Satır 54 Forgot pass sayfasındaki girilen veriyi kontrol edip butonu aktif eder //
        userNameInputFP.addTextChangedListener(watcher)
        // Forgot pass sayfasındaki girilen veriyi kontrol edip butonu aktif eder SON //

    }

    // userNameInputFP daki veriyi izler //
    private val watcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s!!.length >= 10) {
                btn_signin.setBackgroundResource(R.drawable.bg_button_success)
                btn_signin.setTextColor(Color.rgb(56, 211, 58))
                btn_signin.isEnabled = true
            } else {
                btn_signin.setBackgroundResource(R.drawable.bg_button_empty)
                btn_signin.setTextColor(Color.WHITE)
                btn_signin.isEnabled = false
            }
        }

    }
    // userNameInputFP daki veriyi izler SON //


}

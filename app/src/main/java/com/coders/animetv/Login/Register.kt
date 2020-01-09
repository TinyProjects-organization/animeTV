package com.coders.animetv.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.coders.animetv.R
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
    }

    private fun init() {
        /* Register den Logine geri dönüş */
        backtologin.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        /* Register den Logine geri dönüş son*/



        /* Register sayfasındaki show password kısmı */
        showHidePassCheckRegister.setOnClickListener {
            if(showHidePassCheckRegister.isChecked){
                passwordInputRegister.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
            else{
                passwordInputRegister.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
        /* Register sayfasındaki show password kısmı son */



    }


}

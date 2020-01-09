package com.coders.animetv.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.coders.animetv.R
import com.coders.animetv.Utilz.EventBusData
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_register.*
import org.greenrobot.eventbus.EventBus

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

        //Register sayfasından email kodu kontrol sayfasına gider //
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

        //Register sayfasından email kodu kontrol sayfasına gider son //


    }

    override fun onBackPressed() {
        RegisterPageRoot.visibility=View.VISIBLE
        super.onBackPressed()
    }
    //geri tuşuna basılınca geri gideni geri getirir  son//
}

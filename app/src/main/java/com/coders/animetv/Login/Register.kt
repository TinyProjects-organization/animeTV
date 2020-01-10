package com.coders.animetv.Login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.coders.animetv.R
import com.coders.animetv.Utilz.EventBusData
import kotlinx.android.synthetic.main.activity_register.*
import org.greenrobot.eventbus.EventBus
import java.security.AccessController.getContext

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
        // Register den Logine geri dönüş son*/



        // Register sayfasındaki show password kısmı */
        showHidePassCheckRegister.setOnClickListener {
            if(showHidePassCheckRegister.isChecked){
                passwordInputRegister.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
            else{
                passwordInputRegister.transformationMethod = PasswordTransformationMethod.getInstance()
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

        //Register sayfasından email kodu kontrol sayfasına gider son //
*/

        // girelen veriyi takip edip belli kurala göre onaylama //
            eMailInputRegister.addTextChangedListener(watcher)
            userNameInputRegister.addTextChangedListener(watcher)
            passwordInputRegister.addTextChangedListener(watcher)
        // girelen veriyi takip edip belli kurala göre onaylama son//



    }


    /*
   // geri tuşuna basılınca geri gideni geri getirir     //
    override fun onBackPressed() {
        RegisterPageRoot.visibility=View.VISIBLE
        super.onBackPressed()
    }
    //geri tuşuna basılınca geri gideni geri getirir  son//
    */

    //Girelen input kontrol panali //
    val watcher : TextWatcher = object : TextWatcher{
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s!!.length>5){
                if(passwordInputRegister.text.toString().length>5
                    && userNameInputRegister.text.toString().length>5
                    && eMailInputRegister.text.toString().length>5 ){
                    val toast = Toast.makeText(applicationContext, "Egemen Ye beni", Toast.LENGTH_LONG)
                    toast.show()

                }else{

            }

            }else{

            }
        }
    }
    //Girelen input kontrol panali son//
}

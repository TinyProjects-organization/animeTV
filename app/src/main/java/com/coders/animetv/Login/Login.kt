package com.coders.animetv.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.coders.animetv.Homescreen.HomeScreen
import com.coders.animetv.R
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
    }

        private fun init(){

            /* Login sayfasın dan register sayfasına geçiş kısmı TRANSACTION*/
            registerlink.setOnClickListener {
                var transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.registerFragment,RegisterFragment())
                transaction.addToBackStack("register page")
                transaction.commit()
            }


            /* Login sayfasındaki show password kısmı */
            showHidePassCheck.setOnClickListener {
                if(showHidePassCheck.isChecked){
                    passwordInput.transformationMethod = HideReturnsTransformationMethod.getInstance()
                }
                else{
                    passwordInput.transformationMethod = PasswordTransformationMethod.getInstance()
                }
            }

            /* Login den forgot password sayfasona geçiş INTENT */
            forgotPasswordTextView.setOnClickListener{
                val intent = Intent(this, ForgotPasswordActivity::class.java)
                startActivity(intent)
            }

            /*GEÇİCİ OLARAK HOMESCREENE GİDEN İNTENT  */
            btn_signin.setOnClickListener{
                val intent = Intent(this, HomeScreen::class.java)
                startActivity(intent)
            }

    }



}
package com.coders.animetv.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.coders.animetv.R
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
    }

        private fun init(){

            /* Login sayfasın dan register sayfasına geçiş kısmı */
            registerlink.setOnClickListener {
                var transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.registerFragment,RegisterFragment())
                transaction.addToBackStack("register page")
                transaction.commit()
            }


            /* Login sayfasındaki show password kısmı */
            showHideBtn.setOnClickListener {
                if(showHideBtn.text.toString().equals("Show")){
                    passwordInput.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    showHideBtn.text = "Hide"
                } else{
                    passwordInput.transformationMethod = PasswordTransformationMethod.getInstance()
                    showHideBtn.text = "Show"
                }
        }

            /* Login den forgot password sayfasona geçiş  */
            forgotPasswordTextView.setOnClickListener{
                val intent = Intent(this, ForgotPasswordActivity::class.java)
                startActivity(intent)
            }




    }
}
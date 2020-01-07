package com.coders.animetv.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.coders.animetv.R
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
    }

        private fun init(){
            /*Register fragmenti için geçiş butonu*/
            registerlink.setOnClickListener {
                loginRoot.visibility = View.GONE
                var transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.registerFragment,registerFragment())
                transaction.addToBackStack("register")
                transaction.commit()
            }
        }

}

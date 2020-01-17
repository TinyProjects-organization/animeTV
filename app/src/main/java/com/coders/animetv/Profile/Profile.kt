package com.coders.animetv.Profile

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.coders.animetv.Login.Login
import com.coders.animetv.R
import com.coders.animetv.Utilz.BottomNavigationViewManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_homescreen.*
import kotlinx.android.synthetic.main.activity_profile.*

class Profile : AppCompatActivity() {

    // firebase kısmı tanımlama
    lateinit var mAuth: FirebaseAuth
    lateinit var mRef: DatabaseReference
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener

    // hangisinde olduğunu göstermek için //
    private val ACTIVITY_NUMBER = 2
    private val TAG = "Profile Activity"
    // hangisinde olduğunu göstermek için son//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        //hesaba giriş yapılmış mı yapılmamış mı diye
        firebaseAuthListener()

        // navigation oluşturma fonksiyonu
        setupNavigationView()

        //çıkış yap gibi işlemler
        init()

        // firebase tanımlamarı atama  kısmı //
        mAuth = FirebaseAuth.getInstance()
        mRef = FirebaseDatabase.getInstance().reference
        // firebase tanımlamarı atama  kısmı son //

    }

    private fun init() {
        cikisYap.setOnClickListener {
            var dialog = SignOutFragment()
            dialog.show(supportFragmentManager,"çıkış yapılsın mı sorusu")
        }
    }


    // bottom navigation view çalıştırma fonksiyonu //
    fun setupNavigationView() {
        BottomNavigationViewManager.setupBottomNavigationView(bottomNavigationViewProfile)
        BottomNavigationViewManager.setupNavigation(this, bottomNavigationViewProfile)
        var menu = bottomNavigationViewProfile.menu
        var menuItem = menu.getItem(ACTIVITY_NUMBER)
        menuItem.setChecked(true)
    }
    /// bottom navigation view çalıştırma fonksiyonu sonu //



    // kullanıcı giriş yapmış ve çıkış yapmadıysa otomatik giriş gibi işlemler //
    private fun firebaseAuthListener() {
        mAuthListener = FirebaseAuth.AuthStateListener {
            val user = FirebaseAuth.getInstance().currentUser
            // eğer kullanıcı bir dafa giriş yapmış ise çıkış yapana kadar otomatik sisteme sokar //
            if (user == null) {
                val intent = Intent(
                    applicationContext,
                    Login::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
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

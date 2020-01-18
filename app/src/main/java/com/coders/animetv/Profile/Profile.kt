package com.coders.animetv.Profile

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.coders.animetv.Login.Login
import com.coders.animetv.R
import com.coders.animetv.Utilz.BottomNavigationViewManager
import com.google.android.material.bottomnavigation.BottomNavigationView
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

        //Profile page Navigation view kısmı
        setupProfileNavigationView()

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
        BottomNavigationViewManager.setupNavigation(this, bottomNavigationViewProfile,ACTIVITY_NUMBER)
        val menu = bottomNavigationViewProfile.menu
        val menuItem = menu.getItem(ACTIVITY_NUMBER)
        menuItem.isChecked = true

    }
    /// bottom navigation view çalıştırma fonksiyonu sonu //

    // Profile navigation view çalıştırma fonksiyonu //
   private fun setupProfileNavigationView() {
        BottomNavigationViewManager.setupBottomNavigationView(profile_navigation)
        profile_navigation.onNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(p0: MenuItem): Boolean {
                when (p0.itemId) {
                    R.id.favoriteicon -> {
                        var transaction = supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.profile_navbar_layout,favoriteFragment())
                        transaction.addToBackStack(null)
                        transaction.commit()
                        return true
                    }
                    R.id.historyicon -> {
                        var transaction = supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.profile_navbar_layout,historyFragment())
                        transaction.addToBackStack(null)
                        transaction.commit()
                        return true
                    }

                    R.id.editprofileicon -> {
                        var transaction = supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.profile_navbar_layout,editprofileFragment())
                        transaction.addToBackStack(null)
                        transaction.commit()
                        return true
                    }
                }

                return false
            }

        }
    }
    /// profile navigation view çalıştırma fonksiyonu sonu //



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

package com.coders.animetv.Profile


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.net.toUri
import com.coders.animetv.Login.Login
import com.coders.animetv.Models.Users
import com.coders.animetv.R
import com.coders.animetv.Utilz.BottomNavigationViewManager
import com.coders.animetv.Utilz.EventBusData
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_profile.*
import org.greenrobot.eventbus.EventBus

class Profile : AppCompatActivity() {

    // firebase kısmı tanımlama
    lateinit var mAuth: FirebaseAuth
    lateinit var mRef: DatabaseReference
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    //kişi bilgileri çekileceği için hep elimizde tutalım
    lateinit var mUser: FirebaseUser


    // hangisinde olduğunu göstermek için //
    private val ACTIVITY_NUMBER = 2
    private val TAG = "Profile Activity"
    // hangisinde olduğunu göstermek için son//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        //hesaba giriş yapılmış mı yapılmamış mı diye
        firebaseAuthListener()

        // firebase tanımlamarı atama  kısmı //
        mAuth = FirebaseAuth.getInstance()
        mRef = FirebaseDatabase.getInstance().reference
        mUser = mAuth.currentUser!!
        // firebase tanımlamarı atama  kısmı son //

        // navigation oluşturma fonksiyonu
        setupNavigationView()

        //çıkış yap gibi işlemler
        signOutFunc()

        //Üst kısımdaki kulanıcı bilgilerini getirme
        bringUserInfo()

        //Profile page Navigation view kısmı
        setupProfileNavigationView()



    }

    //profile sayfasının üst kısmının firebase  //
    private fun bringUserInfo() {
        mRef.child("users").child("typeC").child(mUser.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }
                override fun onDataChange(p0: DataSnapshot) {

                        if (p0.value != null) {
                            //Firebaseden verilerin tamamnın //
                            val readUser = p0.getValue(Users::class.java)
                            //Firebaseden verilerin tamamnın SON//

                            //Firebase den gelen veriyi eventbus ile gerekli sayfalara gönderme //
                            EventBus.getDefault().postSticky(EventBusData.kullaniciBilgileriniGonder(readUser))
                            //Firebase den gelen veriyi eventbus ile gerekli sayfalara gönderme SON//

                            userNameProfile.text = readUser!!.user_nickname
                            userEmail.text = readUser.user_email
                            //Buraya birde resim çekme eklenicek sonra //

                            if (readUser.user_detail!!.profile_pic !=null) {
                                val toUri = readUser.user_detail!!.profile_pic!!.toUri()
                                imageViewProfile.setImageURI(toUri)
                            }else{
                              //buraya resmi yok ise eklenicek resmi düzenleme
                                //  imageViewProfile.setImageURI()
                            }
                        }
                }
            })

    }
    //profile sayfasının üst kısmının firebase SON //

    private fun signOutFunc() {
        //oturumunun kapatma kodu //
        cikisYap.setOnClickListener {
            val dialog = SignOutFragment()
            dialog.show(supportFragmentManager, "çıkış yapılsın mı sorusu")
        }
        //oturumunun kapatma kodu SON//
    }


    // bottom navigation view çalıştırma fonksiyonu //
    private fun setupNavigationView() {
        BottomNavigationViewManager.setupBottomNavigationView(bottomNavigationViewProfile)
        BottomNavigationViewManager.setupNavigation(
            this,
            bottomNavigationViewProfile,
            ACTIVITY_NUMBER
        )
        val menu = bottomNavigationViewProfile.menu
        val menuItem = menu.getItem(ACTIVITY_NUMBER)
        menuItem.isChecked = true

    }
    /// bottom navigation view çalıştırma fonksiyonu sonu //

    // Profile navigation view işlemleri   //
    private fun setupProfileNavigationView() {
        BottomNavigationViewManager.setupBottomNavigationView(profile_navigation)
        profile_navigation.onNavigationItemSelectedListener =
            object : BottomNavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(p0: MenuItem): Boolean {
                    when (p0.itemId) {
                        R.id.favoriteicon -> {
                            val transaction = supportFragmentManager.beginTransaction()
                            transaction.replace(R.id.profile_navbar_layout, favoriteFragment())
                            transaction.addToBackStack(null)
                            transaction.commit()
                            return true
                        }
                        R.id.historyicon -> {
                            val transaction = supportFragmentManager.beginTransaction()
                            transaction.replace(R.id.profile_navbar_layout, historyFragment())
                            transaction.addToBackStack(null)
                            transaction.commit()
                            return true
                        }

                        R.id.editprofileicon -> {
                            val transaction = supportFragmentManager.beginTransaction()
                            transaction.replace(R.id.profile_navbar_layout, editprofileFragment())
                            transaction.addToBackStack(null)
                            transaction.commit()
                            return true
                        }
                    }

                    return false
                }

            }
    }
    /// profile navigation view işlemleri sonu //


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

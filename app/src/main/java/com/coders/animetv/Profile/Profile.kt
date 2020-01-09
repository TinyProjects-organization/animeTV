package com.coders.animetv.Profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.coders.animetv.R
import com.coders.animetv.Utilz.BottomNavigationViewManager
import kotlinx.android.synthetic.main.activity_homescreen.*
import kotlinx.android.synthetic.main.activity_profile.*

class Profile : AppCompatActivity() {
    // hangisinde olduğunu göstermek için //
    private val  ACTIVITY_NUMBER = 2
    private val TAG = "Profile Activity"
    // hangisinde olduğunu göstermek için son//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupNavigationView()
    }


    // bottom navigation view çalıştırma fonksiyonu //
    fun setupNavigationView() {
        BottomNavigationViewManager.setupBottomNavigationView(bottomNavigationViewProfile)
        BottomNavigationViewManager.setupNavigation(this,bottomNavigationViewProfile)
        var menu = bottomNavigationViewProfile.menu
        var menuItem = menu.getItem(ACTIVITY_NUMBER)
        menuItem.setChecked(true)
    }
    // bottom navigation view çalıştırma fonksiyonu sonu//
}

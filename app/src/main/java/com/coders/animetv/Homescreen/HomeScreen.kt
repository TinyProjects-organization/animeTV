package com.coders.animetv.Homescreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.coders.animetv.R
import com.coders.animetv.Utilz.BottomNavigationViewManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import kotlinx.android.synthetic.main.activity_homescreen.*

class HomeScreen : AppCompatActivity() {
        // hangisinde olduğunu göstermek için //
             private val  ACTIVITY_NUMBER = 0
            private val TAG = "Home Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homescreen)
        setupNavigationView()
    }

    fun setupNavigationView() {
        BottomNavigationViewManager.setupBottomNavigationView(bottomNavigationView)
        BottomNavigationViewManager.setupNavigation(this,bottomNavigationView)
        var menu = bottomNavigationView.menu
        var menuItem = menu.getItem(ACTIVITY_NUMBER)
        menuItem.setChecked(true)
    }
}

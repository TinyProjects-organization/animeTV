package com.coders.animetv.Chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.coders.animetv.R
import com.coders.animetv.Utilz.BottomNavigationViewManager
import kotlinx.android.synthetic.main.activity_chat.*

class Chat : AppCompatActivity() {

    // hangisinde olduğunu göstermek için //
    private val  ACTIVITY_NUMBER = 3
    private val TAG = "Chat Activity"
    // hangisinde olduğunu göstermek için son//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setupNavigationView()
    }



    // bottom navigation view çalıştırma fonksiyonu //
    fun setupNavigationView() {
        BottomNavigationViewManager.setupBottomNavigationView(bottomNavigationViewChat)
        BottomNavigationViewManager.setupNavigation(this,bottomNavigationViewChat)
        var menu = bottomNavigationViewChat.menu
        var menuItem = menu.getItem(ACTIVITY_NUMBER)
        menuItem.setChecked(true)
    }
    // bottom navigation view çalıştırma fonksiyonu sonu//
}

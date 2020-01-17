package com.coders.animetv.List

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.coders.animetv.R
import com.coders.animetv.Utilz.BottomNavigationViewManager
import kotlinx.android.synthetic.main.activity_list.*

class List : AppCompatActivity() {

    // hangisinde olduğunu göstermek için //
    private val ACTIVITY_NUMBER = 0
    private val TAG = "List Activity"
    // hangisinde olduğunu göstermek için son//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setupNavigationView()
    }


    // bottom navigation view çalıştırma fonksiyonu //
    fun setupNavigationView() {
        BottomNavigationViewManager.setupBottomNavigationView(bottomNavigationViewList)
        BottomNavigationViewManager.setupNavigation(this, bottomNavigationViewList,ACTIVITY_NUMBER)
        var menu = bottomNavigationViewList.menu
        var menuItem = menu.getItem(ACTIVITY_NUMBER)
        menuItem.isChecked = true
    }
    // bottom navigation view çalıştırma fonksiyonu sonu//
}

package com.coders.animetv.Utilz

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.coders.animetv.Chat.Chat
import com.coders.animetv.Homescreen.HomeScreen
import com.coders.animetv.List.List
import com.coders.animetv.Profile.Profile
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx

class BottomNavigationViewManager {
    companion object {
        // navigation kısmının efek ve düzenlemesi //
        fun setupBottomNavigationView(bottomNavigationViewEx: BottomNavigationViewEx) {
            bottomNavigationViewEx.enableAnimation(false)
            bottomNavigationViewEx.setTextVisibility(false)
            //  bottomNavigationViewEx.enableShiftingMode(false)
            // bottomNavigationViewEx.enableShiftingMode(false)
        }
        // navigation kısmının efek ve düzenlemesi  son//

        // navigasyonu aktif hale getirme kodu //
        fun setupNavigation(
            context: Context,
            bottomNavigationViewEx: BottomNavigationViewEx,
            sayfaBulma: Int
        ) {
            bottomNavigationViewEx.onNavigationItemSelectedListener =
                object : BottomNavigationView.OnNavigationItemSelectedListener {
                    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                        // herhangi birine tıklandığında açılacak sayfa //
                        when (menuItem.itemId) {
                            com.coders.animetv.R.id.listicon -> {
                                if (sayfaBulma != 0) {
                                    val intent = Intent(
                                        context,
                                        List::class.java
                                    ).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                                    context.startActivity(intent)
                                    (context as AppCompatActivity).overridePendingTransition(0, 0)
                                    return true
                                }
                            }
                            com.coders.animetv.R.id.homeicon -> {
                                if (sayfaBulma != 1) {
                                    val intent = Intent(
                                        context,
                                        HomeScreen::class.java
                                    ).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                                    context.startActivity(intent)
                                    (context as AppCompatActivity).overridePendingTransition(0, 0)
                                    return true
                                }

                            }
                            com.coders.animetv.R.id.profileicon -> {
                                if (sayfaBulma != 2) {
                                    val intent = Intent(
                                        context,
                                        Profile::class.java
                                    ).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                                    context.startActivity(intent)
                                    (context as AppCompatActivity).overridePendingTransition(0, 0)
                                    return true
                                }

                            }
                            com.coders.animetv.R.id.chaticon -> {
                                if (sayfaBulma != 3) {
                                    val intent = Intent(
                                        context,
                                        Chat::class.java
                                    ).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                                    context.startActivity(intent)
                                    (context as AppCompatActivity).overridePendingTransition(0, 0)
                                    return true
                                }

                            }
                        }
                        // eğer üsttekilerden biri çalışmaz ise
                        return false
                    }
                }
        }
        // navigasyonu aktif hale getirme kodu sonu//
    }
}
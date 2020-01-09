package com.coders.animetv.Utilz

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import com.coders.animetv.Chat.Chat
import com.coders.animetv.Homescreen.HomeScreen
import com.coders.animetv.List.List
import com.coders.animetv.Profile.Profile
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx

class BottomNavigationViewManager {
    companion object {
        // navigation kısmının efek ve düzenlemesi //
        fun setupBottomNavigationView(bottomNavigationViewEx: BottomNavigationViewEx){
            bottomNavigationViewEx.enableAnimation(false)
            bottomNavigationViewEx.setTextVisibility(false)
            bottomNavigationViewEx.enableShiftingMode(false)
            bottomNavigationViewEx.enableShiftingMode(false)
        }
        // navigation kısmının efek ve düzenlemesi  son//

        // navigasyonu aktif hale getirme kodu //
        fun setupNavigation (context : Context, bottomNavigationViewEx: BottomNavigationViewEx) {
            bottomNavigationViewEx.onNavigationItemSelectedListener = object :BottomNavigationView.OnNavigationItemSelectedListener{
                override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                    // herhangi birine tıklandığında açılacak sayfa //
                  when(menuItem.itemId){
                      com.coders.animetv.R.id.listicon -> {
                          val intent = Intent(context, List::class.java)
                          context.startActivity(intent)
                          return true
                      }
                      com.coders.animetv.R.id.homeicon -> {
                          val intent = Intent(context, HomeScreen::class.java)
                          context.startActivity(intent)
                          return true
                      }
                      com.coders.animetv.R.id.profileicon -> {
                          val intent = Intent(context, Profile::class.java)
                          context.startActivity(intent)
                          return true
                      }
                      com.coders.animetv.R.id.chaticon -> {
                          //intent oluşturuyoruz intentin içeriği profile activitye gidiyor//
                          val intent = Intent(context, Chat::class.java)
                          context.startActivity(intent)
                          return true
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
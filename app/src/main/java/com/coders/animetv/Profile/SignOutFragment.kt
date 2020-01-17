package com.coders.animetv.Profile


import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

import com.coders.animetv.R
import com.google.firebase.auth.FirebaseAuth

/**
Alert Dialog oluşturma
 */
class SignOutFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var alert = AlertDialog.Builder(this.activity!!).setTitle("Uygulamadan çıkış yap")
            .setMessage("Emin misiniz ?").setPositiveButton("Çıkış yap",object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {

                    //çıkış yapılma işlemi
                    FirebaseAuth.getInstance().signOut()

                    activity!!.finish()

                }
            }).setNegativeButton("İptal et",object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
dismiss()
                }

            }).create()

        return alert
    }

}

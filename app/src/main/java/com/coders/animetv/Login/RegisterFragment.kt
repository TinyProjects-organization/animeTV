package com.coders.animetv.Login


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.coders.animetv.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_register.view.*


class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // eger fragment de işlem yapılacaksa view içine alınıp  o view return yapılıp arasına işlem yazılacak
        val view =inflater.inflate(R.layout.fragment_register, container, false)


        /* Logine basılınca login page ye giden kısım */
        view.backtologin.setOnClickListener{
            activity!!.onBackPressed()
        }




        return view

    }




}

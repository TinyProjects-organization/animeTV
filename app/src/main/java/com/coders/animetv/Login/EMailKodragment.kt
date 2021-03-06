package com.coders.animetv.Login


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast


import com.coders.animetv.R
import com.coders.animetv.Utilz.EventBusData
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_email_kodragment.*
import kotlinx.android.synthetic.main.fragment_email_kodragment.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class EMailKodragment : Fragment() {

    var email = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_email_kodragment, container, false)
        // kullanıcıdan girilen e maili yazar //
        view.userEmailAdress.setText(email)


        view.activationCode.addTextChangedListener(watcher)

        return view
    }


    // gelen e maili sonraki sayfada alma //
    /*
    @Subscribe(sticky = true)
    internal fun eMailEvent(emailkodu: EventBusData.EMailGonder) {
        email = emailkodu.email
        Log.e("Can", "email" + email)

    }

     */

    override fun onAttach(context: Context) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }

    // gelen e maili sonraki sayfada alma son//
    val watcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s!!.length > 5) {
                if (activationCode.text.toString().length > 5) {
                    val toast = Toast.makeText(
                        activity?.applicationContext,
                        "Hello çalisti",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                } else {
                    val toast = Toast.makeText(
                        activity?.applicationContext,
                        "Hello çalisti",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                }
            }
        }
    }

}

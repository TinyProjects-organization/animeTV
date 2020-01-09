package com.coders.animetv.Login


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.coders.animetv.R
import com.coders.animetv.Utilz.EventBusData
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class EMailKodragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email_kodragment, container, false)
    }

    // gelen e maili sonraki sayfada alma //
    @Subscribe (sticky = true)
    internal fun eMailEvent(emailkodu : EventBusData.EMailGonder){
        var email = emailkodu.email
        Log.e("Can","email"+email)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }
    // gelen e maili sonraki sayfada alma son//


}

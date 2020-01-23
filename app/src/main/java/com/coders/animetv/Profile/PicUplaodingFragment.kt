package com.coders.animetv.Profile


import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment

import com.coders.animetv.R
import kotlinx.android.synthetic.main.fragment_pic_uplaoding.*
import kotlinx.android.synthetic.main.fragment_pic_uplaoding.view.*

/**
 * A simple [Fragment] subclass.
 */
class PicUplaodingFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
val view = inflater.inflate(R.layout.fragment_pic_uplaoding, container, false)

        //progress barın rengini ayarlamak
       /*
        view.progressBar.
            indeterminateDrawable.
            setColorFilter(ContextCompat.getColor(activity!!,R.color.colorAccent),PorterDuff.Mode.SRC_IN)

        */

        return view
    }


}

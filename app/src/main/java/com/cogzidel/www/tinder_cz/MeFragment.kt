package com.cogzidel.www.tinder_cz

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class MeFragment : Fragment() {

    companion object {
        fun newInstance(): MeFragment = MeFragment()
    }


    //button Initialize
    var btnLogout: Button? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val Rootview = inflater!!.inflate(R.layout.fragment_me, container, false)

        btnLogout = Rootview.findViewById(R.id.btn_register) as Button

        //Logout Button
        btnLogout?.setOnClickListener(){
            click()
        }

        return Rootview
    }
    private fun click() {
        val i = Intent(getActivity(),LoginActivity::class.java)
        startActivity(i)
    }


}




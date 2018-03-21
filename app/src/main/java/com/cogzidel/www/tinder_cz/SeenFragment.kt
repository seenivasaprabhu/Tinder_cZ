package com.cogzidel.www.tinder_cz

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SeenFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SeenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SeenFragment : Fragment() {

    companion object {
        fun newInstance(): SeenFragment = SeenFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_seen, container, false)
    }
}
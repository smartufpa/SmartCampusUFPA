package br.ufpa.smartufpa.fragments.forms


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import br.ufpa.smartufpa.R

class FormAuditorium : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form_auditoium, container, false)
    }


    companion object {
        @JvmStatic
        fun newInstance() = FormAuditorium()
    }
}

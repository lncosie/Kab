package com.lncosie.zigbee.view

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.view.View
import com.lncosie.kab.R


public class EnterFragment(val title: Int, val type: Int) : DialogFragment(), View.OnClickListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.fragment_enter, null)
        val ok = view.findViewById(R.id.ok)
        ok.setOnClickListener(this)
        val cancel = view.findViewById(R.id.cancel)
        cancel.setOnClickListener(this)
        builder.setView(view)
        return builder.create()
    }

    override fun onClick(v: View?) {

        dismiss()
    }
}
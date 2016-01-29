package com.lncosie.zigbee.view

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.support.design.internal.NavigationMenu
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.lncosie.kab.Kab
import com.lncosie.kab.R
import com.lncosie.kab.event.MenuClickEvent

class MenuFragment(val title: Int, val menu: Int) : DialogFragment(), View.OnClickListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.menu_layout, null) as LinearLayout
        val caption = view.findViewById(R.id.title_textview) as TextView
        caption.setText(title)
        builder.setView(view)
        val tmpmenu = NavigationMenu(context)
        activity.menuInflater.inflate(menu, tmpmenu)
        tmpmenu.visibleItems.forEach {
            val item = inflater.inflate(R.layout.item_menu, view, false) as TextView
            item.text = it.title
            item.id = it.itemId
            item.setOnClickListener(this)
            view.addView(item)
        }
        return builder.create()
    }

    override fun onClick(v: View?) {
        Kab.bus.post(MenuClickEvent(v!!.id, (v as TextView).text))
        this.dismiss()
    }

}
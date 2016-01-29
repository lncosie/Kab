package com.lncosie.zigbee.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lncosie.kab.Kab
import com.lncosie.kab.Layout

public open class PrjFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = javaClass.getAnnotation(Layout::class.java)
        if (layout != null) {
            val root = inflater!!.inflate(layout.value, container, false)
            Kab.bind(this, root)
            return root;
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Kab.unbind(this)
    }
}
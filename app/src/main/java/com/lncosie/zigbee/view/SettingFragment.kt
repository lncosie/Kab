package com.lncosie.zigbee.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lncosie.kab.Layout
import com.lncosie.kab.Named
import com.lncosie.kab.OnClick
import com.lncosie.zigbee.R

@Named("setting")
@Layout(R.layout.fragment_setting)
public open class SettingFragment : PrjFragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    @OnClick(R.id.setting_system)
    public fun Click(v: View) {
        val intent = Intent(context, SystemSettingActivity::class.java)
        startActivity(intent)
    }
}
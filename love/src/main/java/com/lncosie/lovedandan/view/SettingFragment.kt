package com.lncosie.lovedandan.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lncosie.kab.Layout
import com.lncosie.kab.Named
import com.lncosie.kab.OnClick
import com.lncosie.lovedandan.R
import com.lncosie.lovedandan.model.ChatInterface

@Named("x")
@Layout(R.layout.fragment_setting)
public open class SettingFragment : PrjFragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = super.onCreateView(inflater, container, savedInstanceState)
        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
    @OnClick(R.id.switch_user)
    fun switch_user(which:View){
        val lover=ChatInterface.lover!!
        ChatInterface.logout()
        ChatInterface.login(lover,lover)
    }
}
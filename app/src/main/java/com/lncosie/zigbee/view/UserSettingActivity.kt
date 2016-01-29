package com.lncosie.zigbee.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import com.lncosie.kab.Bind
import com.lncosie.kab.Dataview
import com.lncosie.kab.Layout
import com.lncosie.kab.OnClick
import com.lncosie.kab.sql.DataView
import com.lncosie.kab.widget.ListViewAdapter
import com.lncosie.zigbee.R
import com.lncosie.zigbee.model.Device

@Layout(R.layout.activity_user_setting)
open class UserSettingActivity : PrjActivity(R.string.details, R.drawable.back, null) {

    @Bind(R.id.auth_ids)
    lateinit var auth_list: ListView
    lateinit var adapter: ListViewAdapter<Device, Holder>
    @Dataview("select * from device")
    lateinit var devices: DataView<Device>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        devices.update()
        adapter = ListViewAdapter(this, devices, Holder::class.java)
        auth_list.adapter = adapter

    }

    @OnClick(R.id.user_image)
    fun image_clcik(v: View) {
        val intent = Intent(this, UserImageActivity::class.java)
        startActivity(intent)
    }

    class Holder : com.lncosie.kab.widget.ListViewAdapter.ViewHolder<Device> {
        @Bind(R.id.user_name)
        lateinit var user_name: TextView

        override fun viewId(position: Int, data: Device) = R.layout.item_auth
        override fun bind(data: Device) {
            user_name.text = data.name
        }
    }
}

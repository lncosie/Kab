package com.lncosie.zigbee.view

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import com.lncosie.kab.Bind
import com.lncosie.kab.Dataview
import com.lncosie.kab.Layout
import com.lncosie.kab.OnItemClick
import com.lncosie.kab.sql.DataView
import com.lncosie.kab.widget.ListViewAdapter
import com.lncosie.zigbee.R
import com.lncosie.zigbee.model.Device

@Layout(R.layout.activity_device)
open class DeviceActivity : PrjActivity(R.string.devices, R.drawable.back, null) {

    @Bind(R.id.devices)
    lateinit var devices_list: ListView
    lateinit var adapter: ListViewAdapter<Device, Holder>
    @Dataview("select * from device")
    lateinit var devices: DataView<Device>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        devices.update()
        adapter = ListViewAdapter(this, devices, Holder::class.java)
        devices_list.adapter = adapter
    }

    @OnItemClick(R.id.devices)
    fun lock_click(which: Int) {
        val intent = Intent(this, AppActivity::class.java)
        startActivity(intent)
    }

    class Holder : com.lncosie.kab.widget.ListViewAdapter.ViewHolder<Device> {

        @Bind(R.id.user_name)
        lateinit var user_name: TextView

        override fun viewId(position: Int, data: Device): Int = R.layout.item_device
        override fun bind(data: Device) {
            user_name.text = data.name
        }
    }
}

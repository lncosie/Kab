package com.lncosie.zigbee.view

import android.os.Bundle
import android.widget.GridView
import android.widget.TextView
import com.lncosie.kab.Bind
import com.lncosie.kab.Dataview
import com.lncosie.kab.Layout
import com.lncosie.kab.sql.DataView
import com.lncosie.kab.widget.ListViewAdapter
import com.lncosie.zigbee.R
import com.lncosie.zigbee.model.Device

@Layout(R.layout.activity_user_image)
open class UserImageActivity : PrjActivity(R.string.stub, R.drawable.back, null) {

    @Bind(R.id.images)
    lateinit var image_list: GridView
    lateinit var adapter: ListViewAdapter<Device, Holder>

    @Dataview("select * from device")
    lateinit var devices: DataView<Device>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        devices.update()
        adapter = ListViewAdapter(this, devices, Holder::class.java)
        image_list.adapter = adapter
    }

    class Holder : com.lncosie.kab.widget.ListViewAdapter.ViewHolder<Device> {
        override fun viewId(position: Int, data: Device): Int {
            return R.layout.item_image
        }

        @Bind(R.id.user_name)
        lateinit var user_name: TextView

        override fun bind(data: Device) {
            user_name.text = data.name
        }
    }
}

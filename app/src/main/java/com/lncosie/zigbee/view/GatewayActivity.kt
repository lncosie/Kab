package com.lncosie.zigbee.view


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.lncosie.kab.*
import com.lncosie.kab.event.MenuClickEvent
import com.lncosie.kab.sql.DataView
import com.lncosie.kab.widget.ListViewAdapter
import com.lncosie.zigbee.R
import com.lncosie.zigbee.model.Gateway

@Layout(R.layout.activity_gateway)
open class GatewayActivity : PrjActivity(R.string.gateways, null, R.drawable.plus) {
    @Bind(R.id.gateways)
    lateinit var devices_list: ListView
    lateinit var adapter: ListViewAdapter<Gateway, Holder>
    @Dataview("select * from device")
    lateinit var devices: DataView<Gateway>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        devices.update()
        adapter = ListViewAdapter(this, devices, Holder::class.java)
        devices_list.adapter = adapter

    }

    override fun context_action(v: View) {
        val intent = Intent(this, GatewaySearchActivity::class.java)
        startActivityForResult(intent, RESULT_FIRST_USER)
    }

    @OnClick(R.id.search_gateway)
    fun search_gateway(v: View) {
        //val call= Cap()
        //call.call(this)
        //val m=MenuFragment(R.string.setting,R.menu.yesno)
        //m.show(fragmentManager,"")
    }

    @OnItemClick(R.id.gateways)
    fun goto_gateway(position: Int) {
        val intent = Intent(this, DeviceActivity::class.java)
        startActivityForResult(intent, RESULT_FIRST_USER + 1)
    }

    @OnMessage
    fun e(e: MenuClickEvent) {
        Toast.makeText(this, e.title, 0).show()
    }

    class Holder : com.lncosie.kab.widget.ListViewAdapter.ViewHolder<Gateway> {
        override fun viewId(position: Int, data: Gateway) = R.layout.item_gateway
        @Bind(R.id.user_name)
        lateinit var user_name: TextView

        override fun bind(data: Gateway) {
            user_name.text = data.name
        }
    }

}

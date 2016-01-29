package com.lncosie.zigbee.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.RadioButton
import android.widget.TextView
import com.lncosie.kab.*
import com.lncosie.kab.sql.DataView
import com.lncosie.kab.widget.ListViewAdapter
import com.lncosie.zigbee.R
import com.lncosie.zigbee.model.History

@Named("history")
@Layout(R.layout.fragment_history)
public open class HistoryFragment : PrjFragment() {


    lateinit var adapter: ListViewAdapter<History, Holder>

    @Bind(R.id.open_logs_sorter)
    lateinit var open_logs_sorter: LinearLayout
    @Bind(R.id.list_history)
    lateinit var list_history: ListView

    @Bind(R.id.door_button)
    lateinit var door_button: RadioButton


    @Dataview("select * from history")
    lateinit var historys: DataView<History>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = super.onCreateView(inflater, container, savedInstanceState)
        list_history.dividerHeight = 0
        door_button.isChecked = true
        //list_history.setBackgroundColor(R.color.nil)
        historys.update()
        adapter = ListViewAdapter(context, historys, Holder::class.java)
        list_history.adapter = adapter


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    @OnClick(R.id.door_button, R.id.alarm_button)
    fun viewAs(v: View) {
        if (v.getId() == R.id.door_button) {
            historys.sql = "select * from history"
            historys.update()
            open_logs_sorter.visibility = View.VISIBLE
        } else {
            historys.sql = "select * from history limit 5"
            historys.update()
            open_logs_sorter.visibility = View.GONE
        }
        adapter.notifyDataSetChanged()
    }

    class Holder : com.lncosie.kab.widget.ListViewAdapter.ViewHolder<History> {
        override fun viewId(position: Int, data: History): Int = R.layout.item_history
        @Bind(R.id.user_name)
        lateinit var user_name: TextView

        override fun bind(data: History) {
            user_name.text = data.time.toString()
        }
    }
}
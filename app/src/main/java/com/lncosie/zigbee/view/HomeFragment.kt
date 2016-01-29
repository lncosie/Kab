package com.lncosie.zigbee.view

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lncosie.kab.*
import com.lncosie.kab.sql.DataView
import com.lncosie.zigbee.R
import com.lncosie.zigbee.model.History

@Named("home")
@Layout(R.layout.fragment_home)
public open class HomeFragment : PrjFragment() {
    @Dataview("select * from history")
    lateinit var historys: DataView<History>
    lateinit var listViewAdapter: Adapter<History, Holder>
    @Bind(R.id.list_history)
    lateinit var list_history: RecyclerView


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = super.onCreateView(inflater, container, savedInstanceState)
        val llm = LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        list_history.setLayoutManager(llm);
        historys.update()
        listViewAdapter = Adapter(context, historys, Holder::class.java, R.layout.item_history_home)
        list_history.adapter = listViewAdapter
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    class Holder(root: View) : RecyclerView.ViewHolder(root) {
        @Bind(R.id.user_name)
        lateinit var user_name: TextView

        fun bind(data: History) {
            user_name.text = data.time.toString()
        }
    }

    class Adapter<T : History, H : RecyclerView.ViewHolder>(context: Context, val dataset: List<T>, val holder: Class<H>, val layout: Int)
    : RecyclerView.Adapter<H>() {
        val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater;

        override fun getItemCount(): Int {
            return dataset.size
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): H {
            val root = inflator.inflate(layout, parent!!, false)
            val tag = Holder(root);
            Kab.bind(tag, root)
            return tag as H
        }

        override fun onBindViewHolder(holder: H, position: Int) {
            (holder as Holder).bind(dataset.get(position) as T)
        }
    }

}
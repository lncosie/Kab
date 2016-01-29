package com.lncosie.kab.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.lncosie.kab.Kab

class ListViewAdapter<T,H: ListViewAdapter.ViewHolder<T>>(context: Context, var dataset:List<T>,
                                                          var holder:Class<H>,val typeCount:Int=1) : BaseAdapter(){


    interface  ViewHolder<T>{
        fun bind(data:T)
        fun viewId(position: Int,data:T):Int
    }
    val inflator=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater;
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var root=convertView
        val data=dataset.get(position)
        if(convertView==null)
        {
            val tag=holder.newInstance();
            root=inflator.inflate(tag.viewId(position,data),parent!!,false)
            Kab.bind(tag,root)
            root.tag=tag
        }
        val tag=root?.tag as ViewHolder<T>
        tag.bind(data)
        return root
    }

    override fun getItem(position: Int): Any? {
        return dataset.get(position)
    }
    override fun getCount(): Int {
        return dataset.size
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getViewTypeCount()=typeCount
}

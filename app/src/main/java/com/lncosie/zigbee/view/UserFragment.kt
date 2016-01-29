package com.lncosie.zigbee.view


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.lncosie.kab.*
import com.lncosie.kab.sql.DataView
import com.lncosie.kab.widget.ListViewAdapter
import com.lncosie.zigbee.R
import com.lncosie.zigbee.model.User

@Named("user")
@Layout(R.layout.fragment_user)
public open class UserFragment : PrjFragment() {

    @Dataview("select * from user")
    lateinit var users: DataView<User>
    @Bind(R.id.list_users)
    lateinit var list_users: ListView

    lateinit var listViewAdapter: ListViewAdapter<User, Holder>


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = super.onCreateView(inflater, container, savedInstanceState)
        users.update()
        //list_users.setBackgroundColor(R.color.nil)
        listViewAdapter = ListViewAdapter(context, users, Holder::class.java)
        list_users.adapter = listViewAdapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    @OnItemClick(R.id.list_users)
    fun user_click(which: Int) {
        val intent = Intent(context, UserSettingActivity::class.java)
        startActivity(intent)
    }

    class Holder : com.lncosie.kab.widget.ListViewAdapter.ViewHolder<User> {
        @Bind(R.id.user_name)
        lateinit var user_name: TextView
        @Bind(R.id.user_image)
        lateinit var user_image: ImageView

        override fun viewId(position: Int, data: User) = R.layout.item_user
        override fun bind(data: User) {
            user_name.text = data.name
            //user_image.setImageDrawable(BitmapCache.getDrawable(data))
        }
    }

}
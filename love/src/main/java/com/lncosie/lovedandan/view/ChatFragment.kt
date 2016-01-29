package com.lncosie.lovedandan.view
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.lncosie.kab.*
import com.lncosie.kab.sql.DataView
import com.lncosie.kab.widget.ListViewAdapter
import com.lncosie.kab.widget.TextBinding
import com.lncosie.lovedandan.model.Chat
import com.lncosie.lovedandan.R
import com.lncosie.lovedandan.bus.LoginEvent
import com.lncosie.lovedandan.bus.ShowMessage
import com.lncosie.lovedandan.model.ChatHistory
import com.lncosie.lovedandan.model.ChatInterface

@Named("chat")
@Layout(R.layout.fragment_chat)
public open class ChatFragment : PrjFragment(){

    @Dataview("select * from chats_history")
    lateinit var chats: DataView<ChatHistory>

    @Bind(R.id.list_chats)
    lateinit var list: ListView
    lateinit var adapter: ListViewAdapter<ChatHistory,Holder>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root=super.onCreateView(inflater, container, savedInstanceState)
        list.dividerHeight=0
        //chats.update()
        adapter = ListViewAdapter(context, chats,Holder::class.java)
        list.adapter= adapter
        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
    @BindText(R.id.text_send)
    lateinit var text:TextBinding;
    @OnClick(R.id.send)
    public fun user_click(which:View){
        val msg=text.value;
        if(msg.length>0&&ChatInterface.user!=null)
        {
            val chat=Chat()
            chat.message=msg
            chat.user=ChatInterface.user!!
            chat.save()
            Kab.bus.post(ShowMessage(chat))
            ChatInterface.send(ChatInterface.lover!!,msg)
            text.value=""
        }
    }
    @OnMessage
    fun loginEvent(login: LoginEvent){
        if(login.login){
            chats.update()
            (getActivity() as PrjActivity).caption_text=ChatInterface.user!!
        }else{
            chats.clear()
        }
        adapter.notifyDataSetChanged()
    }
    @OnMessage
    fun showMessage(chat:ShowMessage){
        val msg=ChatHistory()
        msg.message=chat.chat.message
        msg.user=chat.chat.user
        chats.add(msg)
        adapter.notifyDataSetChanged()
        list.smoothScrollToPosition(adapter.count)
    }

    class Holder:com.lncosie.kab.widget.ListViewAdapter.ViewHolder<ChatHistory>{
        @Bind(R.id.user_name)
        lateinit var user_name: TextView
        override fun bind(data: ChatHistory) {
            user_name.text=data.message
        }
        override fun viewId(data: ChatHistory): Int {
            if(data.user.equals(ChatInterface.user))
                return R.layout.chat_self
            else
                return R.layout.chat_remote
        }
    }
}
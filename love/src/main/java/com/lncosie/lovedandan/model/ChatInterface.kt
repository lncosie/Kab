package com.lncosie.lovedandan.model

import android.content.Context
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.content.CustomContent
import cn.jpush.im.android.api.enums.ContentType
import cn.jpush.im.android.api.event.MessageEvent
import cn.jpush.im.api.BasicCallback
import com.lncosie.kab.Kab
import com.lncosie.lovedandan.bus.LoginEvent
import com.lncosie.lovedandan.bus.ShowMessage

class ChatInterface {
    companion object {
        private lateinit var glob: ChatInterface
        private var username: String? = null
        public val chatInterface: ChatInterface
            get() {
                return glob
            }
        public val user: String?
            get() {
                return username
            }
        public val lover: String?
            get() {
                if (username == null)
                    return null
                if (username.equals("lncosie")) {
                    return "zhangdan"
                } else
                    return "lncosie"
            }

        public fun jpinit(context: Context) {
            JMessageClient.init(context)
            glob = ChatInterface()
            JMessageClient.registerEventReceiver(glob)
        }

        public fun logout() {
            JMessageClient.logout()
            username = null
            Kab.bus.post(LoginEvent(false))
        }

        public fun login(user: String, password: String) {
            JMessageClient.login(user, password, object : BasicCallback() {
                override fun gotResult(p0: Int, p1: String?) {
                    if (p0 == 0)
                        username = user
                    else
                        username = null
                    Kab.bus.post(LoginEvent(p0 == 0))
                }
            })
        }

        public fun send(id: String, label: String) {
            val map = mapOf(Pair("label", label))
            JMessageClient.sendMessage(JMessageClient.createSingleCustomMessage(id, map))
        }
    }

    fun onEvent(event: MessageEvent) {
        val msg = event.getMessage()
        if(msg.getContentType()!= ContentType.custom)
            return
        val chat = Chat()
        chat.message = (msg.content as CustomContent).getStringValue("label")
        chat.user = msg.fromUser.userName
        chat.save()
        Kab.bus.post(ShowMessage(chat))
        //
        //        when (msg.getContentType()) {
        //            ContentType.text -> {
        //                //处理文字消息
        //                val textContent = msg.getContent() as TextContent
        //                textContent.getText()
        //            }
        //            ContentType.image -> {
        //                //处理图片消息
        //                val imageContent = msg.getContent() as ImageContent
        //                imageContent.getLocalPath()//图片本地地址
        //                imageContent.getLocalThumbnailPath()//图片对应缩略图的本地地址
        //            }
        //            ContentType.voice -> {
        //                //处理语音消息
        //                val voiceContent = msg.getContent() as VoiceContent
        //                voiceContent.getLocalPath()//语音文件本地地址
        //                voiceContent.getDuration()//语音文件时长
        //            }
        //            ContentType.custom -> {
        //                //处理自定义消息
        //                val customContent = msg.getContent() as CustomContent
        //                customContent.getNumberValue("custom_num") //获取自定义的值
        //                customContent.getBooleanValue("custom_boolean")
        //                customContent.getStringValue("custom_string")
        //            }
        //            ContentType.eventNotification -> {
        //                //处理事件提醒消息
        //                val eventNotificationContent = msg.getContent() as EventNotificationContent
        //                when (eventNotificationContent.getEventNotificationType()) {
        //                    EventNotificationContent.EventNotificationType.group_member_added -> {
        //                    }
        //                    EventNotificationContent.EventNotificationType.group_member_removed -> {
        //                    }
        //                }
        //            }
        //        }
    }

}
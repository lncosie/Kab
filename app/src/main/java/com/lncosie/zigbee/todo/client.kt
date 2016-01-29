package com.lncosie.zigbee.todo

import com.squareup.okhttp.Callback
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import java.io.IOException

class client {
    fun c() {
        val c = OkHttpClient()
        c.newCall(Request.Builder().url("").build()).enqueue(
                object : Callback {
                    override fun onFailure(request: Request?, e: IOException?) {
                        throw UnsupportedOperationException()
                    }

                    override fun onResponse(response: Response?) {
                        throw UnsupportedOperationException()
                    }

                })


    }
}
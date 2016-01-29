package com.lncosie.zigbee.net

import android.content.Context
import android.widget.Toast
import retrofit.Callback
import retrofit.GsonConverterFactory
import retrofit.Response
import retrofit.Retrofit

class Cap {
    fun call(c: Context) {
        val base = "http://www.webxml.com.cn"
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(base).build()
        val service = retrofit.create(Hello::class.java)
        service.sayHello().enqueue(object : Callback<String> {
            override fun onResponse(response: Response<String>?, retrofit: Retrofit?) {
                Toast.makeText(c, response?.body(), 0).show()
            }

            override fun onFailure(t: Throwable?) {
                Toast.makeText(c, "flase", 0).show()
            }

        })
    }

}

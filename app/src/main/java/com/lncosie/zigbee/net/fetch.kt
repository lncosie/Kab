package com.lncosie.zigbee.net

import com.lncosie.kab.rx.IPublish
import com.lncosie.kab.rx.RxPublisher
import retrofit.Call
import retrofit.Callback
import retrofit.Response
import retrofit.Retrofit
import retrofit.http.*

class fetch {
    class User {

    }

    interface ZigSdk {
        @Headers(
                "Accept: application/vnd.github.v3.full+json",
                "User-Agent: Retrofit-Sample-App"
        )
        @GET("/users/{user}/repos")
        fun listRepos(@Path("user") user: String): Call<List<User>>

        @GET("/group/{id}/users")
        fun listRepos(@Path("id") user: String, @QueryMap options: Map<String, String>): Call<List<User>>

        @POST("/users/new")
        fun createUser(@Body user: User): Call<User>
    }

    val publish = RxPublisher<List<User>>()
    fun fetch() {
        publish.subcrib(object : IPublish<List<User>> {
            override fun next(iter: List<User>) {
                throw UnsupportedOperationException()
            }

            override fun error(e: Any) {
                throw UnsupportedOperationException()
            }

            override fun complete() {
                throw UnsupportedOperationException()
            }
        })
        val retrofit = Retrofit.Builder().baseUrl("").build()
        val sdk = retrofit.create(ZigSdk::class.java)
        sdk.listRepos("").enqueue(object : Callback<List<User>> {
            override fun onFailure(t: Throwable?) {
            }

            override fun onResponse(response: Response<List<User>>?, retrofit: Retrofit?) {
                publish.next(response!!.body())
            }
        })

    }
}

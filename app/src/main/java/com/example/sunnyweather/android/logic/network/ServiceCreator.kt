package com.example.sunnyweather.android.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

//调用单例类中的方法可以直接使用：Singleton.singletonTest()
object ServiceCreator {

    private const val BASE_URL="https://api.caiyunapp.com/"

    private val retrofit=Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

    fun <T> create(serviceClass: Class<T>):T= retrofit.create(serviceClass)


    //内联函数 的语义很简单： 把函数体复制粘贴到函数调用处
    inline fun <reified T> create():T= create(T::class.java)
}
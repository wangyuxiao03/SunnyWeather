package com.example.sunnyweather.android.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import retrofit2.http.Query
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


//调用单例类中的方法可以直接使用：Singleton.singletonTest()
object SunnyWeatherNetwork {

    private val placeService = ServiceCreator.create(PlaceService::class.java)

    private val weatherService=ServiceCreator.create(WeatherService::class.java)

    //使用了suspend 后 就会切换出来一个协程去执行操作。而不是在当前的线程当中 ，suspend 是暂时切走。等执行完毕后再切回来。
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    suspend fun getDailyWeather(lng:String,lat:String)= weatherService.getDailyWeather(lng,lat).await()

    suspend fun getRealtimeWeather(lng:String,lat:String)= weatherService.getRealtimeWeather(lng,lat).await()

    private suspend fun <T> Call<T>.await(): T {
        return  suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response body is null"))
                }
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                } })
        } }
}
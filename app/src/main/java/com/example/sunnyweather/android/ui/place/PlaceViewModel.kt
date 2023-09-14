package com.example.sunnyweather.android.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.sunnyweather.android.logic.Repository
import com.example.sunnyweather.android.logic.model.Place

class PlaceViewModel : ViewModel() {


    //输入框需要输入的查询内容
    private val searchLiveData=MutableLiveData<String>()

    val placeList= ArrayList<Place>()

    //查询结果livedata
    val placeLiveData= Transformations.switchMap(searchLiveData){query->
        Repository.searchPlaces(query)
    }

    //你点击搜索时调用的方法
    fun searchPlaces(query:String){
        searchLiveData.value=query
    }
}
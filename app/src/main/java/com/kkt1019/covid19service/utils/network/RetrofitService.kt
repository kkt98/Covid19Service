package com.kkt1019.covid19service.utils.network

import com.kkt1019.covid19service.model.Covid19Place
import com.kkt1019.covid19service.model.covid19ItemVO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("15077586/v1/centers")
    fun covid19Data(@Query("serviceKey") serviceKey:String): Call<covid19ItemVO>


//    @Query("page") page:String, @Query("perPage") perPage:String, @Query("returnType") returnType:String,

}
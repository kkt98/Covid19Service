package com.kkt1019.covid19service.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.kkt1019.covid19service.R
import com.kkt1019.covid19service.model.covid19ItemVO
import com.kkt1019.covid19service.utils.RetrofitHelper
import com.kkt1019.covid19service.utils.RetrofitService
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.NaverMapSdk.NaverCloudPlatformClient
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //지도
        NaverMapSdk.getInstance(this).client = NaverCloudPlatformClient("ght6e8nfpq")

        //공공데이터포털 API 받아오기
        val retrofit = RetrofitHelper.covid19Retrofit()
        val retrofitService =retrofit.create(RetrofitService::class.java)
        val call= retrofitService.covid19Data("H7PvoIiO2D6+qVfe6kF2WAoJgdpbVUtJT52Wx7dL6+DLP4IEk5i5xqP+GZMDktix9xaYS03X6YP4JtLGSnuunw==")

        call.enqueue(object : retrofit2.Callback<covid19ItemVO> {
            override fun onResponse(call: Call<covid19ItemVO>, response: Response<covid19ItemVO>) {

                val covid19Response: covid19ItemVO? = response.body()
                Log.i("ccc", covid19Response?.data?.size.toString())

            }

            override fun onFailure(call: Call<covid19ItemVO>, t: Throwable) {
                AlertDialog.Builder(this@MainActivity).setMessage("error : ${t.message}").create().show()
            }

        })
    }
}
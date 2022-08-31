package com.kkt1019.covid19service.repositori

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.kkt1019.covid19service.model.Covid19Place
import com.kkt1019.covid19service.model.covid19ItemVO
import com.kkt1019.covid19service.repositori.db.Covid19Database
import com.kkt1019.covid19service.utils.network.RetrofitHelper
import retrofit2.Call
import retrofit2.Response

class Repository(var context : Context) {

    private val database =  Covid19Database.getDatabase(context)
    private  val dao = database?.covid19Dao()

    var data = dao?.getAllCovid19Place()

    fun getApiData(): List<Covid19Place>? {

        return data

    }

    fun startAPI(){

        //공공데이터포털 API 받아오기
        val retrofit = RetrofitHelper.covid19Retrofit()
        val retrofitService =retrofit.create(RetrofitService::class.java)
        val call= retrofitService.covid19Data("H7PvoIiO2D6+qVfe6kF2WAoJgdpbVUtJT52Wx7dL6+DLP4IEk5i5xqP+GZMDktix9xaYS03X6YP4JtLGSnuunw==")

        call.enqueue(object : retrofit2.Callback<covid19ItemVO> {
            override fun onResponse(call: Call<covid19ItemVO>, response: Response<covid19ItemVO>) {

                var covid19Info : Array<Covid19Place> = response.body()!!.data.toTypedArray()
                var db : Covid19Database? = Covid19Database.getDatabase(context)

                db?.covid19Dao()?.insertCovid19Place(*covid19Info)
                Log.i("kkk", db?.covid19Dao()?.getAllCovid19Place().toString())


            }

            override fun onFailure(call: Call<covid19ItemVO>, t: Throwable) {
                AlertDialog.Builder(context).setMessage("error : ${t.message}").create().show()
            }

        })

    }

}
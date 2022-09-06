package com.kkt1019.covid19service.viewmodel

import android.app.Application
import android.location.Location
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kkt1019.covid19service.model.Covid19Place
import com.kkt1019.covid19service.repositori.Repository
import com.kkt1019.covid19service.repositori.db.Covid19Database
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker

class MyViewModel(application: Application) : AndroidViewModel(application) {


    val getAllCovid19Place : LiveData<List<Covid19Place>>
    val repository : Repository

    private val marker = Marker()

    init {
        val covid19Dao = Covid19Database.getDatabase(application)!!.covid19Dao()

        repository = Repository(application, covid19Dao)

        getAllCovid19Place = repository.getAllCovid19Place

        repository.startAPI()
    }

    class MyViewMocdelFactory(private val application: Application) : ViewModelProvider.Factory  {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return MyViewModel(application) as T
        }

    }
}
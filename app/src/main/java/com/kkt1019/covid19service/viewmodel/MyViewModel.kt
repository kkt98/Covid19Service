package com.kkt1019.covid19service.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kkt1019.covid19service.repositori.Repository

class MyViewModel(application: Application) : AndroidViewModel(application) {

    var repository = Repository(application).getApiData()

    fun asdasd(){

        repository?.get(0)

    }

}
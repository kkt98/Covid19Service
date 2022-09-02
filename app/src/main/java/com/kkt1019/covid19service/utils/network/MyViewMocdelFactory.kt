package com.kkt1019.covid19service.utils.network

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kkt1019.covid19service.viewmodel.MyViewModel


class MyViewMocdelFactory(private val application: Application) : ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return MyViewModel(application) as T
    }

}

//class SetViewModelFactory(private val application: Application) : Factory {
//    override fun <T : ViewModel?> create(aClass: Class<T>): T {
//        return MyVeiwModel(application) as T
//    }
//}
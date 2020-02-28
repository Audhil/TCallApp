package com.example.truecallerapp.ui.main

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import com.example.truecallerapp.model.ResponseWrapper
import com.example.truecallerapp.ui.base.BaseViewModel
import com.example.truecallerapp.util.NetworkError
import javax.inject.Inject

class MainViewModel
@Inject
constructor(
    private val mainRepo: MainRepo
) : BaseViewModel() {

    val progressVisibility = ObservableField<Boolean>(false)
    val t10CharField = ObservableField<String>()
    val e10CharField = ObservableField<String>()
    val wordCountMapField = ObservableField<String>()

    val errorLiveData: LiveData<NetworkError> = mainRepo.errorLiveData

    val responseWrapperLiveData: LiveData<ResponseWrapper> = mainRepo._responseWrapperLiveData

    fun getBlog() = run {
        progressVisibility.set(true)
        compositeDisposable.add(
            mainRepo.grabDataFromServer()
        )
    }
}
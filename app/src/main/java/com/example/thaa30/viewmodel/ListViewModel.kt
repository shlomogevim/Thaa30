package com.example.thaa30.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.thaa30.util.Convers

class ListViewModel(application: Application): AndroidViewModel(application) {
    val convers by lazy { MutableLiveData<List<Convers>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }
    fun refresh(){
        getConvers()
    }

    private fun getConvers() {

        val convesList=ArrayList<Convers>()
        val conv=Convers(1,"על כן ולא","מה כבר ברור בחיים האלה")
        convesList.add(conv)
        for (index in 2..20){
            convesList.add(Convers(index,"Conversation no $index","Some explanation about conversation number : $index"))
        }
        convers.value=convesList
        loadError.value=false
        loading.value=false
    }

}
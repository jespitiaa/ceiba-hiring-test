package com.ceibasoftware.hiringtest.jespitiaa.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.ceibasoftware.hiringtest.jespitiaa.model.User

class UsersViewModel (application: Application) : AndroidViewModel(application) {
    private val _users = MutableLiveData<List<User>>()

    val users: LiveData<List<User>>
        get() = _users
    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {

    }


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UsersViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
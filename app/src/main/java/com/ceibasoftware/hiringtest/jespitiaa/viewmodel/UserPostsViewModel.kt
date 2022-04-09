package com.ceibasoftware.hiringtest.jespitiaa.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.ceibasoftware.hiringtest.jespitiaa.model.Post

class UserPostsViewModel(application: Application) : AndroidViewModel(application) {
    private val _posts = MutableLiveData<List<Post>>()

    val posts: LiveData<List<Post>>
        get() = _posts
    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {

    }


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserPostsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserPostsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}
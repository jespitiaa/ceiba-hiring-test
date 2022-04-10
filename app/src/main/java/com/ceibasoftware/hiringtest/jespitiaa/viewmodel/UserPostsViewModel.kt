package com.ceibasoftware.hiringtest.jespitiaa.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.ceibasoftware.hiringtest.jespitiaa.database.CeibaDB
import com.ceibasoftware.hiringtest.jespitiaa.model.Post
import com.ceibasoftware.hiringtest.jespitiaa.repository.UserPostsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserPostsViewModel(application: Application, private val userId: Int) : AndroidViewModel(application) {
    private val _posts = MutableLiveData<List<Post>>()

    val posts: LiveData<List<Post>>
        get() = _posts

    private val userPostsRepository = UserPostsRepository(application, CeibaDB.getDatabase(application.applicationContext).postsDao())

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _posts.postValue(userPostsRepository.refreshData(userId))
            }
        }
    }


    class Factory(val app: Application, private val userId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserPostsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserPostsViewModel(app, userId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}
package com.ceibasoftware.hiringtest.jespitiaa.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.ceibasoftware.hiringtest.jespitiaa.database.CeibaDB
import com.ceibasoftware.hiringtest.jespitiaa.model.User
import com.ceibasoftware.hiringtest.jespitiaa.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsersViewModel (application: Application) : AndroidViewModel(application) {
    private val _users = MutableLiveData<List<User>>()

    val users: LiveData<List<User>>
        get() = _users

    var userQuery: String = ""

    private val usersRepository = UsersRepository(application, CeibaDB.getDatabase(application.applicationContext).usersDao())
    init {
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                var users = usersRepository.refreshData()
                if(userQuery.isNotEmpty()){
                    users = users.filter {
                        it.name.contains(userQuery, ignoreCase = true)
                    }
                }
                _users.postValue(users)
            }
        }
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
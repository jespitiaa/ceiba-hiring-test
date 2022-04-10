package com.ceibasoftware.hiringtest.jespitiaa.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.ceibasoftware.hiringtest.jespitiaa.database.UserDao
import com.ceibasoftware.hiringtest.jespitiaa.model.User
import com.ceibasoftware.hiringtest.jespitiaa.network.NetworkServiceAdapter

class UsersRepository (val application: Application, private val usersDao: UserDao){
    suspend fun refreshData(): List<User>{
        var cached = usersDao.getUsers()
        return if(cached.isNullOrEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else {
                val users = NetworkServiceAdapter.getInstance(application).getUsers()
                usersDao.insertAll(users)
                users
            }
        } else cached
    }
}
package com.ceibasoftware.hiringtest.jespitiaa.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.ceibasoftware.hiringtest.jespitiaa.database.PostDao
import com.ceibasoftware.hiringtest.jespitiaa.model.Post
import com.ceibasoftware.hiringtest.jespitiaa.network.NetworkServiceAdapter

class UserPostsRepository  (val application: Application, private val postDao: PostDao){
    suspend fun refreshData(userId: Int): List<Post>{
        val cached = postDao.getPosts(userId)
        return if(cached.isNullOrEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else {
                val posts = NetworkServiceAdapter.getInstance(application).getPosts(userId)
                postDao.insertAll(posts)
                posts
            }
        } else cached
    }
}
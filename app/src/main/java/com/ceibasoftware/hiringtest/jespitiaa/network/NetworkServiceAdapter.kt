package com.ceibasoftware.hiringtest.jespitiaa.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ceibasoftware.hiringtest.jespitiaa.model.Post
import com.ceibasoftware.hiringtest.jespitiaa.model.User
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NetworkServiceAdapter constructor(context: Context) {
    companion object{
        const val BASE_URL= "https://jsonplaceholder.typicode.com/"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }
    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }
    suspend fun getUsers()= suspendCoroutine<List<User>>{ cont ->
        requestQueue.add(getRequest("users",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<User>()
                var item: JSONObject?
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    list.add(i, User(item.getInt("id"), item.getString("name"), item.getString("username"), item.getString("email"), item.getString("phone"), item.getString("website")))
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            }))
    }
    suspend fun getPosts(userId:Int) = suspendCoroutine<List<Post>>{ cont->
        requestQueue.add(getRequest("users/$userId/posts",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Post>()
                var item:JSONObject?
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    list.add(i, Post(item.getInt("id"), item.getInt("userId"), item.getString("title"), item.getString("body")))
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            }))
    }
    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }
    private fun postRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.POST, BASE_URL+path, body, responseListener, errorListener)
    }
    private fun putRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.PUT, BASE_URL+path, body, responseListener, errorListener)
    }
}
package com.ceibasoftware.hiringtest.jespitiaa.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ceibasoftware.hiringtest.jespitiaa.R
import com.ceibasoftware.hiringtest.jespitiaa.databinding.PostItemBinding
import com.ceibasoftware.hiringtest.jespitiaa.model.Post

class UserPostsAdapter : RecyclerView.Adapter<UserPostsAdapter.UserPostsViewHolder>(){
    var posts :List<Post> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPostsViewHolder {
        val withDataBinding: PostItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            UserPostsViewHolder.LAYOUT,
            parent,
            false)
        return UserPostsViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: UserPostsViewHolder, position: Int) {
        holder.viewDataBinding.also { it ->
            it.post = posts[position]
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }


    class UserPostsViewHolder(val viewDataBinding: PostItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.user_item
        }
    }

}
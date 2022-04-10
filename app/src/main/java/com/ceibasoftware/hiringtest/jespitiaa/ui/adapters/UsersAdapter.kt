package com.ceibasoftware.hiringtest.jespitiaa.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ceibasoftware.hiringtest.jespitiaa.BR
import com.ceibasoftware.hiringtest.jespitiaa.R
import com.ceibasoftware.hiringtest.jespitiaa.databinding.UserItemBinding
import com.ceibasoftware.hiringtest.jespitiaa.model.User
import com.ceibasoftware.hiringtest.jespitiaa.ui.UsersFragmentDirections

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>(){
    var users :List<User> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val withDataBinding: UserItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            UsersViewHolder.LAYOUT,
            parent,
            false)
        return UsersViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.viewDataBinding.also { it ->
            it.user = users[position]
        }
        holder.viewDataBinding.showPublicationsBtn = true
        holder.viewDataBinding.setVariable(BR.showPublicationsBtn, true)
        holder.viewDataBinding.root.setOnClickListener {
            val action = UsersFragmentDirections.actionUsersFragmentToUserPostsFragment(users[position])
            // Navigate using that action
            holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }


    class UsersViewHolder(val viewDataBinding: UserItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.user_item
        }
    }

}
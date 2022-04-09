package com.ceibasoftware.hiringtest.jespitiaa.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ceibasoftware.hiringtest.jespitiaa.databinding.FragmentUserPostsBinding
import com.ceibasoftware.hiringtest.jespitiaa.databinding.FragmentUsersBinding
import com.ceibasoftware.hiringtest.jespitiaa.model.Post
import com.ceibasoftware.hiringtest.jespitiaa.model.User
import com.ceibasoftware.hiringtest.jespitiaa.ui.adapters.UserPostsAdapter
import com.ceibasoftware.hiringtest.jespitiaa.ui.adapters.UsersAdapter
import com.ceibasoftware.hiringtest.jespitiaa.viewmodel.UserPostsViewModel
import com.ceibasoftware.hiringtest.jespitiaa.viewmodel.UsersViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class UserPostsFragment : Fragment() {

    private var _binding: FragmentUserPostsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: UserPostsViewModel
    private var viewModelAdapter: UserPostsAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserPostsBinding.inflate(inflater, container, false)
        viewModelAdapter = UserPostsAdapter()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.postsRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        viewModel = ViewModelProvider(this, UserPostsViewModel.Factory(activity.application)).get(
            UserPostsViewModel::class.java)
        viewModel.posts.observe(viewLifecycleOwner, Observer<List<Post>> {
            it.apply {
                viewModelAdapter!!.posts = this
            }
        })
    }
}